package com.ymourino.ad02;

/**
 * <p>Clase utilizada para almacenar los datos de los diferentes clientes de la empresa.</p>
 * <p>No se han creado los setter para las propiedades de la clase ya que no son necesarios ahora mismo.</p>
 */
public class Cliente {
    private String nombre;
    private String apellidos;
    private String email;

    /**
     * <p>Constructor de la clase Cliente.</p>
     * <p>Únicamente se limita a inicializar las propiedades del objeto con los datos que recibe en los parámetros.</p>
     *
     * @param nombre Nombre del cliente.
     * @param apellidos Apellidos del cliente.
     * @param email Correo electrónico del cliente.
     */
    public Cliente(String nombre, String apellidos, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
    }

    /**
     * <p>Devuelve el nombre del cliente.</p>
     *
     * @return El nombre del cliente.
     */
    String getNombre() {
        return this.nombre;
    }

    /**
     * <p>Devuelve el nombre completo (nombre + apellidos) del cliente.</p>
     *
     * @return El nombre completo (nombre + apellidos) del cliente.
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

    /**
     * <p>Devuelve el correo electrónico del cliente.</p>
     *
     * @return El correo electrónico del cliente.
     */
    String getEmail() {
        return this.email;
    }
}
