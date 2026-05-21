package com.techlab.articulo.utils;

/**
 * CONSIGNA DE ESTA CLASE
 * ------------------------------------------------------------
 * Esta clase debe concentrar validaciones reutilizables.
 *
 * RECOMENDACIÓN
 * ------------------------------------------------------------
 * Hacerla utilitaria:
 * - clase final
 * - constructor privado
 * - métodos static
 *
 * VALIDACIONES SUGERIDAS
 * ------------------------------------------------------------
 * - validarTextoNoVacio(String texto)
 * - validarLongitudMaxima(String texto, int maximo)
 * - validarNoNegativo(int valor)
 * - validarNoNegativo(double valor)
 *
 * BENEFICIO
 * ------------------------------------------------------------
 * Evita repetir la misma lógica de validación en distintas partes
 * del sistema.
 */
public final class Validaciones {

    private Validaciones() {
    }

    // TODO:
    // Implementar validaciones necesarias.
    public static boolean validarTextoNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
    // Validar longitud máxima.
    public static boolean validarLongitudMaxima(String texto, int maximo) {
        if (texto == null) {
            return false;
        }
        return texto.trim().length() <= maximo;
    }
    // Validar no negativo (int).
    public static boolean validarNoNegativo(int valor) {
        return valor >= 0;
    }
    // Validar no negativo (double).
    public static boolean validarNoNegativo(double valor) {
        return valor >= 0;
    }

}
