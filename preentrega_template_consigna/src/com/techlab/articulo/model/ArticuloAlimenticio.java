package com.techlab.articulo.model;

/**
 * CONSIGNA DE ESTA CLASE
 * ------------------------------------------------------------
 * Esta clase debe heredar de Articulo.
 *
 * Representa un artículo alimenticio.
 *
 * ATRIBUTO ESPECÍFICO
 * ------------------------------------------------------------
 * - diasParaVencimiento : int
 *
 * ESTA CLASE DEBE
 * ------------------------------------------------------------
 * - tener constructor
 * - tener getters y setters
 * - sobrescribir getTipoArticulo()
 * - sobrescribir calcularPrecioFinal()
 *
 * IDEA DIDÁCTICA PARA calcularPrecioFinal()
 * ------------------------------------------------------------
 * Podés definir una regla propia, por ejemplo:
 * - si faltan pocos días para vencer, aplicar descuento
 * - si no, mantener el precio
 */
public class ArticuloAlimenticio extends Articulo {

    private int diasParaVencimiento;

    // TODO:
    // Crear constructor.
    public ArticuloAlimenticio(int codigo, String nombre, double precio, Categoria categoria, int diasParaVencimiento){
        super(codigo, nombre, precio, categoria);
        this.diasParaVencimiento = diasParaVencimiento;

    }

    // TODO:
    // Crear getters y setters.
    public int getDiasParaVencimiento() {
        return diasParaVencimiento;
    }
    public void setDiasParaVencimiento(int diasParaVencimiento) {
        this.diasParaVencimiento = diasParaVencimiento;
    }

    @Override
    public String getTipoArticulo() {
        // TODO:
        return "Alimenticio";
    }

    @Override
    protected String getDetalleEspecifico() {
        return "Vense en: " + diasParaVencimiento +" días.";
    }

    @Override
    public double calcularPrecioFinal() {
        // TODO:
        // Implementar lógica propia del artículo alimenticio.
        if(diasParaVencimiento <= 3) {
            return precio * 0.8; // Aplicar un descuento del 20% si faltan 5 días o menos para vencer
        }
        return precio; // Mantener el precio si faltan más de 5 días para vencer
    }

    @Override
    public String toString() {
        return super.toString() + ", tipo: " + getTipoArticulo();
    }
}
