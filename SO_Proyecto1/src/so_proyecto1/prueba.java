/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so_proyecto1;

/**
 *
 * @author luism
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class prueba {
    public static void main(String[] args) {
        System.out.println("ssss");
        // Define las listas de programas simulados (cada elemento representa una instrucción).
        List<String> programa1 = new LinkedList<>();
        programa1.add("Instrucción 1");
        programa1.add("Instrucción 2");
        programa1.add("Instrucción 3");
        // Agrega más instrucciones si es necesario.

        List<String> programa2 = new LinkedList<>();
        programa2.add("Instrucción 1");
        programa2.add("Instrucción 2");
        // Agrega más instrucciones si es necesario.

        List<String> programa3 = new LinkedList<>();
        programa3.add("Instrucción 1");
        programa3.add("Instrucción 2");
        programa3.add("Instrucción 3");
        // Agrega más instrucciones si es necesario.

        List<String> programa4 = new LinkedList<>();
        programa4.add("Instrucción 1");
        programa4.add("Instrucción 2");
        programa4.add("Instrucción 3");
        // Agrega más instrucciones si es necesario.

        List<String> programa5 = new LinkedList<>();
        programa5.add("Instrucción 1");
        programa5.add("Instrucción 2");
        // Agrega más instrucciones si es necesario.

        // Crea una cola para administrar los procesos (programas).
        Queue<List<String>> colaProcesos = new LinkedList<>();
        colaProcesos.add(programa1);
        colaProcesos.add(programa2);
        colaProcesos.add(programa3);
        colaProcesos.add(programa4);
        colaProcesos.add(programa5);

        // Define el quantum.
        int quantum = 2;

        // Simula el algoritmo Round Robin.
        while (!colaProcesos.isEmpty()) {
            List<String> programaActual = colaProcesos.poll();
            System.out.println("Ejecutando programa: " + programaActual);

            // Ejecuta el programa actual durante el quantum o hasta que termine.
            for (int i = 0; i < quantum && !programaActual.isEmpty(); i++) {
                String instruccion = programaActual.remove(0);
                System.out.println("Ejecutando instrucción: " + instruccion);
            }

            // Si el programa actual no ha terminado, vuelve a encolarlo.
            if (!programaActual.isEmpty()) {
                colaProcesos.add(programaActual);
            }
        }

        System.out.println("Todos los programas han sido ejecutados.");
    }
}

