/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simuladormaquinaturing;

/**
 *
 * @author Usuario
 */
public class MaquinaTuring {
    
 private LectorArchivo definicion;
    private Cinta cinta;
    private String estadoActual;
    private char simboloBlanco;

    // Limite de seguridad para evitar simulaciones infinitas.
    private static final int LIMITE_PASOS = 10000;

    /**
     * Constructor de la maquina.
     */
    public MaquinaTuring(LectorArchivo definicion, String entrada) {
        this.definicion = definicion;
        
        // Detectamos cual es el simbolo blanco en el alfabeto de la maquina.
        // Por lo general es 'B', '_' o '#'. Si no lo encuentra, usa '_' por defecto.
        this.simboloBlanco = '_';
        for (char c : definicion.getAlfabeto()) {
            if (c == 'B' || c == '#' || c == '_' || c == ' ') {
                this.simboloBlanco = c;
                break;
            }
        }

        validarEntrada(entrada);

        // Inicializamos la cinta pasandole el simbolo blanco correcto
        this.cinta = new Cinta(entrada, this.simboloBlanco);
        this.estadoActual = definicion.getEstadoInicial();
    }

    /**
     * Valida que todos los simbolos pertenezcan al alfabeto.
     */
    private void validarEntrada(String entrada) {
        for (char simbolo : entrada.toCharArray()) {
            if (!definicion.getAlfabeto().contains(simbolo)) {
                throw new IllegalArgumentException(
                        "Simbolo no permitido en la entrada: " + simbolo
                );
            }
        }
    }

    /**
     * Busca una transicion segun el estado y simbolo actuales.
     */
    private Transicion buscarTransicion(String estado, char simbolo) {
        for (Transicion transicion : definicion.getTransiciones()) {
            if (transicion.getEstadoActual().equals(estado)
                    && transicion.getSimboloLeido() == simbolo) {
                return transicion;
            }
        }
        return null;
    }

    /**
     * Ejecuta la simulacion de la Maquina de Turing.
     */
    public boolean ejecutar() {
        int paso = 0;

        System.out.println("\n======================================");
        System.out.println("       INICIO DE LA SIMULACION");
        System.out.println("======================================");

        while (true) {
            // Comprobar si se alcanzo el estado de aceptacion.
            if (estadoActual.equals(definicion.getEstadoAceptacion())) {
                mostrarResultado(true);
                return true;
            }

            // Comprobar si se alcanzo el estado de rechazo.
            if (estadoActual.equals(definicion.getEstadoRechazo())) {
                mostrarResultado(false);
                return false;
            }

            // Evitar ejecuciones que nunca terminan.
            if (paso >= LIMITE_PASOS) {
                System.out.println("\nSIMULACION DETENIDA");
                System.out.println("Se alcanzo el limite de pasos.");
                System.out.println("No se puede determinar el resultado.");
                return false;
            }

            System.out.println("\nPaso: " + paso);
            System.out.println("Estado actual: " + estadoActual);

            cinta.mostrar();

            // Leer el simbolo actual.
            char simboloLeido = cinta.leer();
            System.out.println("Simbolo leido: " + simboloLeido);

            // Buscar la transicion correspondiente.
            Transicion transicion = buscarTransicion(estadoActual, simboloLeido);

            // Si no existe una transicion, la maquina rechaza.
            if (transicion == null) {
                System.out.println("No existe una transicion para esta situacion.");
                estadoActual = definicion.getEstadoRechazo();
                mostrarResultado(false);
                return false;
            }

            // Mostrar la regla que se ejecutara.
            System.out.println("Siguiente estado: " + transicion.getSiguienteEstado());
            System.out.println("Simbolo escrito: " + transicion.getSimboloEscrito());
            System.out.println("Movimiento: " + transicion.getMovimiento());

            // Escribir el nuevo simbolo.
            cinta.escribir(transicion.getSimboloEscrito());

            // Mover la cabeza lectora.
            cinta.mover(transicion.getMovimiento());

            // Actualizar el estado.
            estadoActual = transicion.getSiguienteEstado();
            paso++;
        }
    }

    /**
     * Muestra el resultado final.
     */
    private void mostrarResultado(boolean aceptada) {
        System.out.println("\n======================================");
        if (aceptada) {
            System.out.println("       CADENA ACEPTADA");
        } else {
            System.out.println("       CADENA RECHAZADA");
        }
        System.out.println("======================================");
        System.out.println("Estado final: " + estadoActual);
        System.out.println("Cinta final: " + cinta.obtenerContenido());
    }
}
