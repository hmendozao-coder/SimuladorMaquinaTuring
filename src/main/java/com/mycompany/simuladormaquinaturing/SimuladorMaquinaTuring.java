/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.simuladormaquinaturing;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

/**
 *
 * @Henry Mendoza
 */
public class SimuladorMaquinaTuring {

   public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        LectorArchivo definicion = null;
        int opcion;

        System.out.println("======================================");
        System.out.println("     SIMULADOR DE MAQUINA DE TURING");
        System.out.println("======================================");

        do {
            System.out.println("\n============== MENU PRINCIPAL ==============");
            System.out.println("1. Cargar definicion de Maquina de Turing");
            System.out.println("2. Ingresar cadena y ejecutar simulacion");
            System.out.println("3. Salir");
            System.out.println("============================================");
            System.out.print("Seleccione una opcion: ");

            String seleccion = teclado.nextLine().trim();

            try {
                opcion = Integer.parseInt(seleccion);
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {

                case 1:
                    System.out.println("\n--- CARGAR MAQUINA ---");
                    System.out.println("Sugerencia: si el archivo esta en la carpeta del proyecto,");
                    System.out.println("solo escriba: maquina.txt");
                    System.out.print("Ingrese la ruta del archivo .txt: ");

                    String ruta = teclado.nextLine().trim();
                    
                    // Validar que el usuario no haya dejado vacío
                    if (ruta.isEmpty()) {
                        System.out.println("\nError: La ruta no puede estar vacía.");
                        break;
                    }

                    // Validar si el archivo existe usando la clase File
                    File archivo = new File(ruta);
                    if (!archivo.exists() || archivo.isDirectory()) {
                        System.out.println("\nError: El archivo no existe en la ruta especificada.");
                        System.out.println("Asegurese de incluir el nombre del archivo y su extension (ej: maquina.txt).");
                        System.out.println("Ruta ingresada: " + ruta);
                        break;
                    }

                    try {
                        LectorArchivo nuevaDefinicion = new LectorArchivo();
                        nuevaDefinicion.cargar(ruta);
                        definicion = nuevaDefinicion;

                        System.out.println("\nMaquina cargada correctamente.");
                        System.out.println("Estado inicial: " + definicion.getEstadoInicial());
                        System.out.println("Estado de aceptacion: " + definicion.getEstadoAceptacion());
                        System.out.println("Estado de rechazo: " + definicion.getEstadoRechazo());
                        System.out.println("Cantidad de estados: " + definicion.getEstados().size());
                        System.out.println("Cantidad de transiciones: " + definicion.getTransiciones().size());

                    } catch (IOException | IllegalArgumentException e) {
                        System.out.println("\nError al procesar el archivo: " + e.getMessage());
                    }
                    break;

                case 2:
                    if (definicion == null) {
                        System.out.println("\nPrimero debe cargar una maquina.");
                        break;
                    }

                    System.out.println("\n--- INGRESO DE CADENA ---");
                    System.out.println("Ingrese la cadena que desea evaluar.");
                    System.out.print("Cadena: ");

                    String entrada = teclado.nextLine();

                    try {
                        MaquinaTuring maquina = new MaquinaTuring(definicion, entrada);
                        maquina.ejecutar();
                    } catch (IllegalArgumentException e) {
                        System.out.println("\nError en la cadena: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("\nGracias por utilizar el simulador.");
                    break;

                default:
                    System.out.println("\nOpcion no valida. Intente nuevamente.");
            }

        } while (opcion != 3);

        teclado.close();
    }
}