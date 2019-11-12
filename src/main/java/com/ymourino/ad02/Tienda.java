package com.ymourino.ad02;

import java.util.LinkedHashMap;

/**
 * <p>Clase para gestionar los empleados y productos de una tienda.</p>
 */
public class Tienda {
    private String nombre;
    private String ciudad;
    private Integer siguienteEmpleado;
    private LinkedHashMap<Integer, Empleado> empleados;
    private LinkedHashMap<String, Producto> productos;

    /**
     * <p>Constructor de la clase Tienda.</p>
     * <p>Únicamente inicializa las propiedades de la clase con los datos que recibe en los parámetros.</p>
     *
     * @param nombre Nombre de la tienda.
     * @param ciudad Ciudad donde está ubicada la tienda.
     */
    public Tienda(String nombre, String ciudad) {
        this.nombre = nombre;
        this.ciudad = ciudad;

        // El contador de empleados se debe a que no hay una forma óptima de obtener el índice del
        // "último" empleado (tampoco hay realmente un último empleado, pues son estructuras de
        // tipo Map). Con este contador evitamos que se repita un índice y sobreescribir empleados.
        // Además se inicializa en 1 para dar posibilidad en algunos menús de salir usando el 0.
        siguienteEmpleado = new Integer(1);
        empleados = new LinkedHashMap<>();
        productos = new LinkedHashMap<>();
    }

    String getNombre() {
        return this.nombre;
    }

    String getCiudad() {
        return this.ciudad;
    }

    /**
     * <p>Añade un nuevo empleado a la estructura correspondiente.</p>
     *
     * @param nombre Nombre del nuevo empleado.
     * @param apellidos Apellidos del nuevo empleado.
     */
    public void anhadirEmpleado(String nombre, String apellidos) {
        empleados.put(siguienteEmpleado, new Empleado(nombre, apellidos));
        siguienteEmpleado++;
    }

    /**
     * <p>Añade un nuevo producto a la estructura correspondiente.</p>
     * <p>Este método recibe un objeto ya creado, dado que antes de su creación se realiza una comprobación para evitar
     * duplicados. Ya que esta comprobación requiere de una interacción con el usuario, he preferido no tenerla
     * en un método como este.</p>
     *
     * @param id Identificador del producto. Se utiliza como clave en la estructura que los almacena.
     * @param producto Objeto de la clase Producto creado previamente.
     */
    public void anhadirProducto(String id, Producto producto) {
        productos.put(id, producto);
    }

    /**
     * <p>Dada una clave previamente validada, se elimina el empleado correspondiente.</p>
     * <p>La clave también debería validarse en este método, pero en esta ocasión queda como tarea pendiente.</p>
     *
     * @param key Clave del empleado a eliminar.
     */
    public void eliminarEmpleado(Integer key) {
        empleados.remove(key);
    }

    /**
     * <p>Dada una clave previamente validada, se elimina el producto correspondiente.</p>
     * <p>La clave también debería validarse en este método, pero en esta ocasión queda como tarea pendiente.</p>
     *
     * @param key Clave (identificador) del producto a eliminar.
     */
    public void eliminarProducto(String key) {
        productos.remove(key);
    }

    /**
     * <p>Dada una clave, devuelve el empleado correspondiente, o null si la clave no se corresponde con ningún empleado.</p>
     *
     * @param key Clave del empleado a recuperar.
     * @return Empleado correspondiente a la clave, o null si la clave no tiene correspondencia.
     */
    public Empleado getEmpleado(Integer key) {
        return empleados.get(key);
    }

    /**
     * <p>Dada una clave, devuelve el producto correspondiente, o null si la clave no se corresponde con ningún producto.</p>
     *
     * @param key Clave del producto a recuperar.
     * @return Producto correspondiente a la clave, o null si la clave no tiene correspondencia.
     */
    public Producto getProducto(String key) {
        return productos.get(key);
    }

    /**
     * <p>Muestra en pantalla un listado de los empleados de la tienda.</p>
     *
     * @return true si hay empleados y se han listado, false en caso contrario.
     */
    public boolean listarEmpleados() {
        System.out.println();
        if (empleados.size() > 0) {
            for (Integer k : empleados.keySet()) {
                System.out.println(k + ". " + empleados.get(k).getNombreCompleto());
            }

            System.out.println();
            return true;
        } else {
            System.out.println("NO EXISTEN EMPLEADOS EN LA TIENDA");
            return false;
        }
    }

    /**
     * <p>Muestra en pantalla un listado de los productos de la tienda.</p>
     *
     * @return true si hay productos y se han listado, false en caso contrario.
     */
    public boolean listarProductos() {
        System.out.println();
        if (productos.size() > 0) {
            for (String k : productos.keySet()) {
                Producto p = productos.get(k);

                System.out.println("Identificador: " + k);
                System.out.println("  Descripción: " + p.getDescripcion());
                System.out.println("       Precio: " + p.getPrecio());
                System.out.println("        Stock: " + p.getCantidad());
                System.out.println();
            }

            return true;
        } else {
            System.out.println("NO EXISTEN PRODUCTOS EN LA TIENDA");
            return false;
        }
    }
}
