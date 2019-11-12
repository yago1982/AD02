package com.ymourino.ad02;

/**
 * <p>Clase utilizada para almacenar los datos de los diferentes productos de las tiendas.</p>
 * <p>No se han creado los setter para las propiedades de la clase ya que no son necesarios ahora mismo.</p>
 */
public class Producto {
    private String identificador;
    private String descripcion;
    private float precio;
    private int cantidad;

    /**
     * <p>Constructor de la clase Producto.</p>
     * <p>Únicamente se limita a inicializar las propiedades del objeto con los datos que recibe en los parámetros.</p>
     *
     * @param identificador Identificador del producto.
     * @param descripcion Descripción del producto.
     * @param precio Precio unitario del producto.
     * @param cantidad Cantidad disponible inicial de producto.
     */
    public Producto(String identificador, String descripcion, float precio, int cantidad) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    /**
     * <p>Devuelve el identificador del producto.</p>
     *
     * @return El identificador del producto.
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * <p>Devuelve la descripción del producto.</p>
     *
     * @return La descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * <p>Devuelve el precio del producto.</p>
     *
     * @return El precio del producto.
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * <p>Devuelve la cantidad disponible de producto.</p>
     *
     * @return La cantidad disponible de producto.
     */
    public int getCantidad() {
        return cantidad;
    }
}
