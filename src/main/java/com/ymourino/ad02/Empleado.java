package com.ymourino.ad02;

/**
 * <p>Clase utilizada para almacenar los datos de los diferentes empleados de las tiendas.</p>
 * <p>No se han creado los setter para las propiedades de la clase ya que no son necesarios ahora mismo.</p>
 */
public class Empleado {
    private String nombre;
    private String apellidos;

    /**
     * <p>Constructor de la clase Empleado.</p>
     * <p>Únicamente se limita a inicializar las propiedades del objeto con los datos que recibe en los parámetros.</p>
     *
     * @param nombre Nombre del empleado.
     * @param apellidos Apellidos del empleado.
     */
    public Empleado(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    /**
     * <p>Devuelve el nombre del empleado.</p>
     *
     * @return El nombre del empleado.
     */
    String getNombre() {
        return this.nombre;
    }

    /**
     * <p>Devuelve el nombre completo (nombre + apellidos) del empleado.</p>
     *
     * @return El nombre completo (nombre + apellidos) del empleado.
     */
    String getNombreCompleto() {
        return this.nombre + " " + this.apellidos;
    }

    /**
     * <p>Devuelve los apellidos del cliente.</p>
     *
     * @return Los apellidos del cliente.
     */
    String getApellidos() {
        return this.apellidos;
    }
}
