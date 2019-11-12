package com.ymourino.ad02;

import java.util.LinkedHashMap;

/**
 * <p>Clase utilizada para gestionar las tiendas y clientes de la empresa.</p>
 */
public class Empresa {
    private Integer siguienteTienda;
    private Integer siguienteCliente;
    private LinkedHashMap<Integer, Tienda> tiendas;
    private LinkedHashMap<Integer, Cliente> clientes;

    /**
     * <p>Constructor de la clase Empresa.</p>
     * <p>Únicamente inicializa las propiedades de la clase con unos valores iniciales conocidos.</p>
     */
    public Empresa() {
        // Los contadores de tiendas y clientes se deben a que no hay una forma óptima de obtener el índice del
        // "último" elemento de tiendas y clientes (tampoco hay realmente un último elemento, pues son estructuras de
        // tipo Map). Con estos contadores evitamos que se repita un índice y sobreescribir tiendas o clientes.
        // Además se inicializan en 1 para dar posibilidad en algunos menús de salir usando el 0.
        siguienteTienda = new Integer(1);
        siguienteCliente = new Integer(1);
        tiendas = new LinkedHashMap<>();
        clientes = new LinkedHashMap<>();
    }

    /**
     * <p>Añade una nueva tienda a la estructura correspondiente.</p>
     *
     * @param nombre Nombre de la nueva tienda.
     * @param ciudad Nombre de la ciudad donde se ubica la nueva tienda.
     */
    public void anhadirTienda(String nombre, String ciudad) {
        tiendas.put(siguienteTienda, new Tienda(nombre, ciudad));
        siguienteTienda++;
    }

    /**
     * <p>Añade un nuevo cliente a la estructura correspondiente.</p>
     *
     * @param nombre Nombre del nuevo cliente.
     * @param apellidos Apellidos del nuevo cliente.
     * @param email Correo electrónico del nuevo cliente.
     */
    public void anhadirCliente(String nombre, String apellidos, String email) {
        clientes.put(siguienteCliente, new Cliente(nombre, apellidos, email));
        siguienteCliente++;
    }

    /**
     * <p>Dada una clave previamente validada, se elimina la tienda correspondiente.</p>
     * <p>La clave también debería validarse en este método, pero en esta ocasión queda como tarea pendiente.</p>
     *
     * @param key Clave de la tienda a eliminar.
     */
    public void eliminarTienda(Integer key) {
        tiendas.remove(key);
    }

    /**
     * <p>Dada una clave previamente validada, se elimina el cliente correspondiente.</p>
     * <p>La clave también debería validarse en este método, pero en esta ocasión queda como tarea pendiente.</p>
     *
     * @param key Clave del cliente a eliminar.
     */
    public void eliminarCliente(Integer key) {
        clientes.remove(key);
    }

    /**
     * <p>Dada una clave, devuelve la tienda correspondiente, o null si la clave no se corresponde con ninguna tienda.</p>
     *
     * @param key Clave de la tienda a recuperar.
     * @return Tienda correspondiente a la clave, o null si la clave no tiene correspondencia.
     */
    public Tienda getTienda(Integer key) {
        return tiendas.get(key);
    }

    /**
     * <p>Dada una clave, devuelve el cliente correspondiente, o null si la clave no se corresponde con ningún cliente.</p>
     *
     * @param key Clave del cliente a recuperar.
     * @return Cliente correspondiente a la clave, o null si la clave no tiene correspondencia.
     */
    public Cliente getCliente(Integer key) {
        return clientes.get(key);
    }

    /**
     * <p>Muestra en pantalla un listado de las tiendas existentes.</p>
     *
     * @return true si hay tiendas y se han listado, false en caso contrario.
     */
    public boolean listarTiendas() {
        System.out.println();
        if (tiendas.size() > 0) {
            for (Integer k : tiendas.keySet()) {
                System.out.println(k + ". " + tiendas.get(k).getNombre() + " - " + tiendas.get(k).getCiudad());
            }

            System.out.println();
            return true;
        } else {
            System.out.println("NO EXISTEN TIENDAS EN EL SISTEMA");
            return false;
        }
    }

    /**
     * <p>Muestra en pantalla un listado de los clientes existentes.</p>
     *
     * @return true si hay clientes y se han listado, false en caso contrario.
     */
    public boolean listarClientes() {
        System.out.println();
        if (clientes.size() > 0) {
            for (Integer k : clientes.keySet()) {
                System.out.println(k + ". " + clientes.get(k).getNombreCompleto() + " (" + clientes.get(k).getEmail() + ")");
            }

            System.out.println();
            return true;
        } else {
            System.out.println("NO EXISTEN CLIENTES EN EL SISTEMA");
            return false;
        }
    }
}
