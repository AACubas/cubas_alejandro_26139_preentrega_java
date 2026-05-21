package com.techlab.articulo;

// ============================================================
// CONSIGNA GENERAL DE LA PREENTREGA
// ============================================================
//
// Desarrollar una aplicación en Java por consola que permita gestionar
// artículos y categorías de una tienda.
//
// La aplicación deberá trabajar con:
// - Programación Orientada a Objetos
// - Clase abstracta
// - Herencia
// - Polimorfismo
// - Interfaces
// - Generics
// - Relación entre objetos
// - Menús separados
//
// ESTRUCTURA CONCEPTUAL ESPERADA
// ------------------------------------------------------------
// 1) Debe existir una clase abstracta Articulo con:
//    - codigo
//    - nombre
//    - precio
//    - categoria (objeto Categoria, NO String)
//
// 2) Deben existir dos clases hijas de Articulo:
//    - ArticuloElectronico
//    - ArticuloAlimenticio
//
// 3) Debe existir una clase Categoria con su propio CRUD.
//
// 4) Debe existir una interfaz Calculable con el método:
//    - double calcularPrecioFinal();
//
// 5) Debe existir una interfaz Identificable con el método:
//    - int getCodigo();
//
// 6) Debe existir un Repositorio<T extends Identificable>
//    para administrar artículos y categorías en memoria.
//
// 7) Debe existir una clase base Menu y dos menús hijos:
//    - MenuArticulos
//    - MenuCategorias
//
// REGLAS DE NEGOCIO PRINCIPALES
// ------------------------------------------------------------
// - No se puede crear un artículo si no existen categorías cargadas.
// - No se puede eliminar una categoría si tiene artículos asociados.
// - Los códigos de artículos y categorías deben ser automáticos.
// - Deben validarse todos los datos ingresados.
// - No debe resolverse toda la lógica dentro del main.
//
// IMPORTANTE
// ------------------------------------------------------------
// Este proyecto se entrega como PLANTILLA.
// Eso significa que:
// - la estructura ya está armada,
// - los nombres de las clases ya están definidos,
// - y en cada archivo encontrarás instrucciones específicas
//   sobre qué implementar.
//
// Tu tarea será completar cada clase respetando esta estructura.
//

// Importamos Scanner para leer datos por consola.
import java.util.Scanner;

// Importamos los menús que luego deberán completarse.
import com.techlab.articulo.menu.MenuArticulos;
import com.techlab.articulo.menu.MenuCategorias;

import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.Categoria;

import com.techlab.articulo.repository.Repositorio;

public class App {

    public static void main(String[] args) {

        // ============================================================
        // INSTRUCCIONES PARA ESTA CLASE
        // ============================================================
        //
        // Esta clase debe actuar como punto de entrada del programa.
        //
        // ¿Qué debe hacer el alumno acá?
        // 1) Crear un Scanner compartido.
        // 2) Crear los repositorios necesarios:
        //    - Repositorio<Articulo>
        //    - Repositorio<Categoria>
        // 3) Crear los dos menús:
        //    - MenuArticulos
        //    - MenuCategorias
        // 4) Crear un menú principal que permita elegir:
        //    - 1: ir al menú de artículos
        //    - 2: ir al menú de categorías
        //    - 0: salir
        // 5) Cerrar correctamente el Scanner al finalizar.
        //
        // Sugerencia:
        // El main NO debe tener toda la lógica del sistema.
        // El main solo debe coordinar el flujo principal.
        //
        // ============================================================

        Scanner scanner = new Scanner(System.in);

        // TODO:
        // Crear aquí los repositorios genéricos.
            
         Repositorio<Articulo> repositorioArticulos = new Repositorio<>();
         Repositorio<Categoria> repositorioCategorias = new Repositorio<>();

        // TODO:
        // Crear aquí los menús y pasarles lo que necesiten por constructor.
        MenuCategorias menuCategorias = new MenuCategorias(
                scanner,
                repositorioCategorias,
                repositorioArticulos
        );
        MenuArticulos menuArticulos = new MenuArticulos(
                scanner,
                repositorioArticulos,
                repositorioCategorias
        );

        // TODO:
        // Implementar el menú principal de la aplicación.
        int opcion;
        
        do {
            // Mostramos el encabezado del sistema.
            System.out.println("\n==========================================================");
            System.out.println(" SISTEMA DE GESTIÓN PARA LA PREENTREGA DE JAVA POR CONSOLA");
            System.out.println("==========================================================");
            System.out.println("1 - Menú de artículos");
            System.out.println("2 - Menú de categorías");
            System.out.println("0 - Salir");
            System.out.println("==========================================================1");

            // Leemos la opción del usuario utilizando un método auxiliar.
            opcion = leerEntero(scanner, "Ingrese una opción: ");

            // Evaluamos la opción elegida.
            switch (opcion) {
                case 1:
                    // Si elige 1, ejecutamos el submenú de artículos.
                    menuArticulos.ejecutar();
                    break;

                case 2:
                    // Si elige 2, ejecutamos el submenú de categorías.
                    menuCategorias.ejecutar();
                    break;

                case 0:
                    // Si elige 0, mostramos mensaje de salida.
                    System.out.println("\nGracias por utilizar el sistema. ¡Hasta luego!");
                    break;

                default:
                    // Si la opción no existe, informamos el error.
                    System.out.println("\nError: la opción ingresada no es válida.");
            }

        } while (opcion != 0);

        scanner.close();
    }

       private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                // Mostramos el mensaje de pedido.
                System.out.print(mensaje);

                // Leemos la línea completa y la convertimos a entero.
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Si falla la conversión, avisamos y repetimos.
                System.out.println("Error: debe ingresar un número entero válido.");
            }
        }
    }
}
