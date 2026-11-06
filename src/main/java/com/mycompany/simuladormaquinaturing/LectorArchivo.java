/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simuladormaquinaturing;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Henry Mendoza
 */
public class LectorArchivo {
    
 private List<Estado> estados;
    private String estadoInicial;
    private String estadoAceptacion;
    private String estadoRechazo;
    private Set<Character> alfabeto;
    private List<Transicion> transiciones;

    public LectorArchivo() {
        estados = new ArrayList<>();
        alfabeto = new HashSet<>();
        transiciones = new ArrayList<>();
    }

    /**
     * Carga la definicion desde un archivo de texto.
     */
    public void cargar(String ruta) throws IOException {

        List<String> lineas = Files.readAllLines(
                Path.of(ruta), StandardCharsets.UTF_8);

        estados.clear();
        alfabeto.clear();
        transiciones.clear();

        estadoInicial = null;
        estadoAceptacion = null;
        estadoRechazo = null;

        int numeroLinea = 0;

        for (String lineaOriginal : lineas) {

            numeroLinea++;
            String linea = lineaOriginal.trim();

            if (linea.isEmpty() || linea.startsWith("#")) {
                continue;
            }

            if (linea.startsWith("Estados")) {

                String contenido = extraerContenido(linea, "Estados");

                for (String nombre : contenido.split(",")) {
                    estados.add(new Estado(nombre.trim()));
                }

            } else if (linea.startsWith("Inicial")) {

                estadoInicial = extraerContenido(linea, "Inicial");

            } else if (linea.startsWith("Ha")) {

                estadoAceptacion = extraerContenido(linea, "Ha");

            } else if (linea.startsWith("He")) {

                estadoRechazo = extraerContenido(linea, "He");

            } else if (linea.startsWith("Alfabeto")) {

                String contenido = extraerContenido(linea, "Alfabeto");

                for (String simbolo : contenido.split(",")) {

                    simbolo = simbolo.trim();

                    if (simbolo.length() != 1) {
                        throw new IllegalArgumentException(
                                "Simbolo no valido en linea " + numeroLinea);
                    }

                    alfabeto.add(simbolo.charAt(0));
                }

            } else if (linea.startsWith("MT")) {

                Pattern patron = Pattern.compile(
                    "^MT\\s*\\(\\s*([^,()]+)\\s*,\\s*(\\S)\\s*\\)"
                    + "\\s*=\\s*\\(\\s*([^,()]+)\\s*,\\s*(\\S)"
                    + "\\s*,\\s*([DIN])\\s*\\)$"
                );

                Matcher matcher = patron.matcher(linea);

                if (!matcher.matches()) {
                    throw new IllegalArgumentException(
                            "Transicion incorrecta en linea " + numeroLinea);
                }

                String actual = matcher.group(1).trim();
                char leido = matcher.group(2).charAt(0);
                String siguiente = matcher.group(3).trim();
                char escrito = matcher.group(4).charAt(0);
                char movimiento = matcher.group(5).charAt(0);

                transiciones.add(new Transicion(
                        actual, leido, siguiente, escrito, movimiento));

            } else {

                throw new IllegalArgumentException(
                        "Instruccion desconocida en linea " + numeroLinea);
            }
        }

        validarDefinicion();
    }

    /**
     * Extrae el contenido que se encuentra entre parentesis.
     */
    private String extraerContenido(String linea, String instruccion) {

        Pattern patron = Pattern.compile(
                "^" + instruccion + "\\s*\\(\\s*(.*?)\\s*\\)\\s*$"
        );

        Matcher matcher = patron.matcher(linea);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Formato incorrecto: " + linea);
        }

        return matcher.group(1).trim();
    }

    /**
     * Comprueba que los estados y transiciones sean validos.
     */
    private void validarDefinicion() {

        Set<String> nombres = new HashSet<>();

        for (Estado estado : estados) {
            nombres.add(estado.getNombre());
        }

        if (nombres.size() != estados.size() || nombres.isEmpty()) {
            throw new IllegalArgumentException(
                    "Estados duplicados o lista de estados vacia.");
        }

        if (estadoInicial == null
                || estadoAceptacion == null
                || estadoRechazo == null
                || alfabeto.isEmpty()) {

            throw new IllegalArgumentException(
                    "Faltan datos obligatorios de la maquina.");
        }

        if (!nombres.contains(estadoInicial)
                || !nombres.contains(estadoAceptacion)
                || !nombres.contains(estadoRechazo)) {

            throw new IllegalArgumentException(
                    "Se utilizo un estado que no esta declarado.");
        }

        if (estadoAceptacion.equals(estadoRechazo)) {
            throw new IllegalArgumentException(
                    "Aceptacion y rechazo deben ser estados diferentes.");
        }

        for (Transicion t : transiciones) {

            if (!nombres.contains(t.getEstadoActual())
                    || !nombres.contains(t.getSiguienteEstado())) {

                throw new IllegalArgumentException(
                        "Una transicion utiliza un estado no declarado.");
            }

            if (!alfabeto.contains(t.getSimboloLeido())
                    || !alfabeto.contains(t.getSimboloEscrito())) {

                throw new IllegalArgumentException(
                        "Una transicion utiliza simbolos fuera del alfabeto.");
            }
        }
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public String getEstadoAceptacion() {
        return estadoAceptacion;
    }

    public String getEstadoRechazo() {
        return estadoRechazo;
    }

    public Set<Character> getAlfabeto() {
        return alfabeto;
    }

    public List<Transicion> getTransiciones() {
        return transiciones;
    }
}
