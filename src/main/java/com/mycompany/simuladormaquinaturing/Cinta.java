/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simuladormaquinaturing;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Henry Mendoza
 */

/**
 * Representa la cinta de la Maquina de Turing.
 * Permite leer, escribir y desplazar la cabeza lectora.
 */

public class Cinta {
    
 // Simbolo utilizado para representar espacios en blanco (dinámico).
    private char simboloBlanco;

    // Lista que almacena los simbolos de la cinta.
    private List<Character> simbolos;

    // Posicion actual de la cabeza lectora.
    private int posicionCabeza;

    /**
     * Constructor de la cinta.
     * Recibe la cadena inicial y el símbolo blanco definido en el archivo.
     */
    public Cinta(String entrada, char simboloBlanco) {
        this.simboloBlanco = simboloBlanco;
        simbolos = new ArrayList<>();

        // Cargar los caracteres de entrada.
        for (char caracter : entrada.toCharArray()) {
            simbolos.add(caracter);
        }

        // Si la entrada esta vacia, agregar un blanco.
        if (simbolos.isEmpty()) {
            simbolos.add(this.simboloBlanco);
        }

        posicionCabeza = 0;
    }

    /**
     * Lee el simbolo de la posicion actual.
     */
    public char leer() {
        return simbolos.get(posicionCabeza);
    }

    /**
     * Escribe un simbolo en la posicion actual.
     */
    public void escribir(char simbolo) {
        simbolos.set(posicionCabeza, simbolo);
    }

    /**
     * Desplaza la cabeza lectora.
     * D = Derecha
     * I = Izquierda
     * N = Sin movimiento
     */
    public void mover(char movimiento) {
        switch (movimiento) {
            case 'D':
                posicionCabeza++;
                if (posicionCabeza >= simbolos.size()) {
                    simbolos.add(this.simboloBlanco);
                }
                break;

            case 'I':
                if (posicionCabeza == 0) {
                    simbolos.add(0, this.simboloBlanco);
                } else {
                    posicionCabeza--;
                }
                break;

            case 'N':
                break;

            default:
                throw new IllegalArgumentException("Movimiento no valido: " + movimiento);
        }
    }

    /**
     * Obtiene el contenido completo de la cinta.
     */
    public String obtenerContenido() {
        StringBuilder contenido = new StringBuilder();
        for (char simbolo : simbolos) {
            contenido.append(simbolo);
        }
        return contenido.toString();
    }

    /**
     * Muestra la cinta y la posicion de la cabeza lectora.
     */
    public void mostrar() {
        System.out.println("Cinta: " + obtenerContenido());
        for (int i = 0; i < simbolos.size(); i++) {
            if (i == posicionCabeza) {
                System.out.print("  ^ ");
            } else {
                System.out.print("    ");
            }
        }
        System.out.println();
        System.out.println("Cabeza lectora: " + leer());
    }
}