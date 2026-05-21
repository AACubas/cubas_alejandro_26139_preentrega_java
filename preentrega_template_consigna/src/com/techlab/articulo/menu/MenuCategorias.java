package com.techlab.articulo.menu;

// Importamos listas porque mostraremos y recorreremos colecciones.
import java.util.List;
import java.util.Scanner;

// Importamos los modelos necesarios.
import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.Categoria;

// Importamos el repositorio genérico.
import com.techlab.articulo.repository.Repositorio;

// Importamos utilidades de secuencias y validaciones.
import com.techlab.articulo.utils.Secuencias;
import com.techlab.articulo.utils.Validaciones;

/**
 * CONSIGNA DE ESTA CLASE
 * ------------------------------------------------------------
 * Esta clase debe heredar de Menu y encargarse del CRUD de categorías.
 *
 * Debe trabajar con:
 * - Repositorio<Categoria>
 * - Repositorio<Articulo>
 *
 * ¿Por qué necesita también artículos?
 * Porque antes de eliminar una categoría debe verificarse si está
 * siendo utilizada por algún artículo.
 *
 * FUNCIONALIDADES ESPERADAS
 * ------------------------------------------------------------
 * 1) Ingresar categoría
 * 2) Listar categorías
 * 3) Consultar una categoría por código
 * 4) Modificar una categoría
 * 5) Eliminar una categoría
 * 0) Volver
 *
 * VALIDACIONES
 * ------------------------------------------------------------
 * - nombre no vacío
 * - descripción no vacía
 * - no permitir categorías repetidas por nombre
 *
 * REGLA DE NEGOCIO IMPORTANTE
 * ------------------------------------------------------------
 * No se puede eliminar una categoría si existe al menos un artículo
 * asociado a ella.
 *
 * SUGERENCIA DE MÉTODOS
 * ------------------------------------------------------------
 * - ingresarCategoria()
 * - listarCategorias()
 * - consultarCategoria()
 * - modificarCategoria()
 * - eliminarCategoria()
 * - categoriaTieneArticulosAsociados(...)
 */
public class MenuCategorias extends Menu {

    private final Repositorio<Categoria> repositorioCategorias;
    private final Repositorio<Articulo> repositorioArticulos;

        public MenuCategorias(
            Scanner scanner,
            Repositorio<Categoria> repositorioCategorias,
            Repositorio<Articulo> repositorioArticulos
    ) {
        super(scanner);
        this.repositorioCategorias = repositorioCategorias;
        this.repositorioArticulos = repositorioArticulos;
    }


    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ CATEGORÍAS ---");
        System.out.println("1 - Ingresar categoría");
        System.out.println("2 - Listar categorías");
        System.out.println("3 - Consultar categoría");
        System.out.println("4 - Modificar categoría");
        System.out.println("5 - Eliminar categoría");
        System.out.println("0 - Volver");
    }

    @Override
    public void ejecutar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    ingresarCategoria();
                    break;
                case 2:
                    listarCategorias();
                    break;
                case 3:
                    consultarCategoria();
                    break;
                case 4:
                    modificarCategoria();
                    break;
                case 5:
                    eliminarCategoria();
                    break;
                case 0:
                    System.out.println("\nVolviendo al menú principal...");
                    break;
                default:
                    System.out.println("\nError: la opción ingresada no es válida.");
            }

        } while (opcion != 0);
    }


    // TODO:
    // Implementar todos los métodos del CRUD de categorías.

    /**
     * Alta de una categoría.
     */
    private void ingresarCategoria() {
        System.out.println("\nAlta de categoría");

        String nombre = pedirNombreCategoria(-1);
        String descripcion = pedirDescripcionCategoria();

        int codigo = Secuencias.generarCodigoCategoria();

        Categoria categoria = new Categoria(codigo, nombre, descripcion);

        boolean agregada = repositorioCategorias.agregar(categoria);

        if (agregada) {
            System.out.println("\nCategoría ingresada correctamente.");
            System.out.println(categoria);
        } else {
            System.out.println("\nError: no se pudo guardar la categoría.");
        }
    }

    /**
     * Muestra todas las categorías cargadas.
     */
    private void listarCategorias() {
        List<Categoria> categorias = repositorioCategorias.listar();

        if (categorias.isEmpty()) {
            System.out.println("\nNo hay categorías cargadas para mostrar.");
            return;
        }

        System.out.println("\nListado de categorías:");
        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    /**
     * Consulta una categoría por código.
     */
    private void consultarCategoria() {
        if (repositorioCategorias.estaVacio()) {
            System.out.println("\nNo hay categorías cargadas.");
            return;
        }

        listarCategorias();

        int codigo = leerEntero("\nIngrese el código de la categoría a consultar: ");
        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);

        if (categoria == null) {
            System.out.println("\nError: no existe una categoría con ese código.");
            return;
        }

        System.out.println("\nCategoría encontrada:");
        System.out.println(categoria);
    }

    /**
     * Modifica una categoría existente.
     */
    private void modificarCategoria() {
        if (repositorioCategorias.estaVacio()) {
            System.out.println("\nNo hay categorías cargadas.");
            return;
        }

        listarCategorias();

        int codigo = leerEntero("\nIngrese el código de la categoría a modificar: ");
        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);

        if (categoria == null) {
            System.out.println("\nError: no existe una categoría con ese código.");
            return;
        }

        System.out.println("\nCategoría seleccionada:");
        System.out.println(categoria);

        if (leerSiNo("\n¿Desea modificar el nombre? (S/N): ")) {
            categoria.setNombre(pedirNombreCategoria(categoria.getCodigo()));
        }

        if (leerSiNo("¿Desea modificar la descripción? (S/N): ")) {
            categoria.setDescripcion(pedirDescripcionCategoria());
        }

        System.out.println("\nCategoría modificada correctamente.");
        System.out.println(categoria);
    }

    /**
     * Elimina una categoría, pero solo si no está asociada a artículos.
     *
     * Esta validación es importante para mantener integridad lógica:
     * no tiene sentido eliminar una categoría que está siendo utilizada.
     */
    private void eliminarCategoria() {
        if (repositorioCategorias.estaVacio()) {
            System.out.println("\nNo hay categorías cargadas.");
            return;
        }

        listarCategorias();

        int codigo = leerEntero("\nIngrese el código de la categoría a eliminar: ");
        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);

        if (categoria == null) {
            System.out.println("\nError: no existe una categoría con ese código.");
            return;
        }

        if (categoriaTieneArticulosAsociados(categoria)) {
            System.out.println("\nNo se puede eliminar la categoría porque tiene artículos asociados.");
            return;
        }

        System.out.println("\nCategoría seleccionada:");
        System.out.println(categoria);

        boolean confirmar = leerSiNo("¿Confirma la eliminación? (S/N): ");

        if (!confirmar) {
            System.out.println("\nOperación cancelada por el usuario.");
            return;
        }

        boolean eliminada = repositorioCategorias.eliminar(categoria);

        if (eliminada) {
            System.out.println("\nCategoría eliminada correctamente.");
        } else {
            System.out.println("\nError: no se pudo eliminar la categoría.");
        }
    }

    /**
     * Solicita y valida el nombre de una categoría.
     *
     * Además:
     * - no permite texto vacío
     * - no permite superar longitud máxima
     * - no permite nombres repetidos
     *
     * @param codigoExcluir código que no debe compararse (útil en modificación).
     * @return nombre válido.
     */
    private String pedirNombreCategoria(int codigoExcluir) {
        while (true) {
            String nombre = leerTexto("Ingrese el nombre de la categoría: ");

            if (!Validaciones.validarTextoNoVacio(nombre)) {
                System.out.println("Error: el nombre no puede estar vacío.");
                continue;
            }

            if (!Validaciones.validarLongitudMaxima(nombre, 40)) {
                System.out.println("Error: el nombre no puede superar los 40 caracteres.");
                continue;
            }

            if (existeNombreCategoria(nombre.trim(), codigoExcluir)) {
                System.out.println("Error: ya existe una categoría con ese nombre.");
                continue;
            }

            return nombre.trim();
        }
    }

    /**
     * Solicita y valida la descripción de una categoría.
     *
     * @return descripción válida.
     */
    private String pedirDescripcionCategoria() {
        while (true) {
            String descripcion = leerTexto("Ingrese la descripción de la categoría: ");

            if (!Validaciones.validarTextoNoVacio(descripcion)) {
                System.out.println("Error: la descripción no puede estar vacía.");
                continue;
            }

            if (!Validaciones.validarLongitudMaxima(descripcion, 100)) {
                System.out.println("Error: la descripción no puede superar los 100 caracteres.");
                continue;
            }

            return descripcion.trim();
        }
    }

    /**
     * Verifica si ya existe una categoría con el mismo nombre.
     *
     * @param nombre nombre a buscar.
     * @param codigoExcluir código que no debe compararse.
     * @return true si existe, false si no existe.
     */
    private boolean existeNombreCategoria(String nombre, int codigoExcluir) {
        for (Categoria categoria : repositorioCategorias.listar()) {
            boolean mismoCodigo = categoria.getCodigo() == codigoExcluir;
            boolean mismoNombre = categoria.getNombre().equalsIgnoreCase(nombre);

            if (!mismoCodigo && mismoNombre) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si una categoría está asociada a al menos un artículo.
     *
     * @param categoria categoría a verificar.
     * @return true si tiene artículos asociados, false en caso contrario.
     */
    private boolean categoriaTieneArticulosAsociados(Categoria categoria) {
        for (Articulo articulo : repositorioArticulos.listar()) {
            if (articulo.getCategoria().getCodigo() == categoria.getCodigo()) {
                return true;
            }
        }
        return false;
    }
}
