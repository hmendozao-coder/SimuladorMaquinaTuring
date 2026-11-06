/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simuladormaquinaturing;

/**
 *
 * @Henry Mendoza
 */
public class Transicion {
    
 private String estadoActual;
    private char simboloLeido;
    private String siguienteEstado;
    private char simboloEscrito;
    private char movimiento;

    public Transicion(String estadoActual, char simboloLeido,
            String siguienteEstado, char simboloEscrito,
            char movimiento) {

        this.estadoActual = estadoActual;
        this.simboloLeido = simboloLeido;
        this.siguienteEstado = siguienteEstado;
        this.simboloEscrito = simboloEscrito;
        this.movimiento = movimiento;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public char getSimboloLeido() {
        return simboloLeido;
    }

    public String getSiguienteEstado() {
        return siguienteEstado;
    }

    public char getSimboloEscrito() {
        return simboloEscrito;
    }

    public char getMovimiento() {
        return movimiento;
    }
}
