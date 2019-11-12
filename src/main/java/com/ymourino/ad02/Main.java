package com.ymourino.ad02;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.ymourino.ad02.utils.Command;
import com.ymourino.ad02.utils.CommandPrompt;
import com.ymourino.ad02.utils.KeyboardReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * <p>Clase principal de la aplicación.</p>
 */
public class Main {
    // La clase Empresa engloba todos los elementos de la aplicación para poder guardar y recuperar los datos con la librería
    // GSON.
    private static Empresa empresa;

    /**
     * <p>Punto de entrada de la aplicación.</p>
     * <p>Crea una nueva línea de comandos, añadiéndole todos los posibles comandos y asociándolos con los métodos
     * correspondientes.</p>
     *
     * @param args Parámetros de la línea de comandos (no se usan ahora).
     */
    public static void main(String[] args) {
        loadJSON();

        CommandPrompt.withCommands(
                Command.withName("1").withDescription("Añadir una tienda").withMethod(Main::anhadirTienda),
                Command.withName("2").withDescription("Listar tiendas").withMethod(Main::listarTiendas),
                Command.withName("3").withDescription("Eliminar una tienda").withMethod(Main::eliminarTienda),
                Command.withName("4").withDescription("Añadir un producto a una tienda").withMethod(Main::anhadirProducto),
                Command.withName("5").withDescription("Listar productos de una tienda").withMethod(Main::listarProductos),
                Command.withName("6").withDescription("Eliminar un producto de una tienda").withMethod(Main::eliminarProducto),
                Command.withName("7").withDescription("Añadir un empleado a una tienda").withMethod(Main::anhadirEmpleado),
                Command.withName("8").withDescription("Listar empleados de una tienda").withMethod(Main::listarEmpleados),
                Command.withName("9").withDescription("Eliminar un empleado de una tienda").withMethod(Main::eliminarEmpleado),
                Command.withName("10").withDescription("Añadir un cliente").withMethod(Main::anhadirCliente),
                Command.withName("11").withDescription("Listar clientes").withMethod(Main::listarClientes),
                Command.withName("12").withDescription("Eliminar un cliente").withMethod(Main::eliminarCliente),
                Command.withName("13").withDescription("Crear copia de seguridad").withMethod(() -> saveJSON("empresa.backup")),
                Command.withName("14").withDescription("Leer los titulares de El País").withMethod(Main::titulares),
                Command.withName("15").withDescription("Salir del programa").withMethod(() -> {
                    System.out.println();
                    System.exit(0);
                })
        )
                .withPrompt("[AD02] >> ")
                .run();
    }

    /**
     * <p>Solicita los datos necesarios para crear una nueva tienda y llama al método correspondiente en la clase
     * <b>Empresa</b> para que realice dicha creación y almacene la nueva tienda. Tras esto se guardan los cambios en el
     * fichero <b>json</b>.</p>
     */
    private static void anhadirTienda() {
        System.out.println();
        String nombre = KeyboardReader.readString("Introduzca el nombre de la tienda: ",
                "Error con el nombre introducido");
        String ciudad = KeyboardReader.readString("Introduzca la ciudad de la tienda: ",
                "Error con la ciudad introducida");
        empresa.anhadirTienda(nombre, ciudad);
        saveJSON("empresa.json");
    }

    /**
     * <p>Usa el método <b>listarTiendas</b> de la clase <b>Empresa</b> para mostrar por pantalla las tiendas existentes.</p>
     */
    private static void listarTiendas() {
        empresa.listarTiendas();
    }

    /**
     * <p>Solicita al usuario la clave de una tienda a eliminar. La comprobación sobre la clave se realiza en el método
     * <b>seleccionarTienda</b>, de modo que no se le pase al método <b>eliminarTienda</b> una clave inexistente.
     * Al terminar se guardan los cambios en el fichero <b>json</b>.</p>
     */
    private static void eliminarTienda() {
        Integer keyTienda = seleccionarTienda();

        if (keyTienda != null && confirmarSiNo("¿Realmente quiere eliminar la tienda? (S/N): ",
                "Eliminación cancelada")) {
            empresa.eliminarTienda(keyTienda);
            saveJSON("empresa.json");
        }
    }

    /**
     * <p>Solicita los datos necesarios para crear un nuevo producto. Antes de la creación se comprueba si el identificador
     * que proporciona el usuario ya existe en la tienda elegida. En caso de que no exista, se crea el producto y se añade
     * a la tienda. Al terminar se guardan los cambios en el fichero <b>json</b>.</p>
     */
    private static void anhadirProducto() {
        Integer keyTienda = seleccionarTienda();

        if (keyTienda != null) {
            String identificador = KeyboardReader.readString("Introduzca el identificador del producto: ",
                    "Error con el identificador introducido");

            // Se comprueba que el identificador no exista en la tienda.
            while (empresa.getTienda(keyTienda).getProducto(identificador) != null) {
                System.out.println("El producto ya existe en esta tienda");
                identificador = KeyboardReader.readString("Introduzca el identificador del producto: ",
                        "Error con el identificador introducido");
            }

            String descripcion = KeyboardReader.readString("Introduzca la descripción del producto: ",
                    "Error con la descripción introducida");
            float precio = KeyboardReader.readFloat("Introduzca el precio del producto: ",
                    "Error con el precio introducido");
            int cantidad = KeyboardReader.readInt("Introduzca el stock del producto: ",
                    "Error con la cantidad introducida");
            empresa.getTienda(keyTienda).anhadirProducto(identificador,
                    new Producto(identificador, descripcion, precio, cantidad));
            saveJSON("empresa.json");
        }
    }

    /**
     * <p>Usa el método <b>listarProductos</b> de la clase <b>Tienda</b> para mostrar por pantalla los productos de una
     * tienda determinada.</p>
     */
    private static void listarProductos() {
        Integer keyTienda = seleccionarTienda();

        if (keyTienda != null) {
            empresa.getTienda(keyTienda).listarProductos();
        }
    }

    /**
     * <p>Permite seleccionar una tienda determinada y, tras mostrar los productos de dicha tienda, permite seleccionar
     * el que queramos eliminar.</p>
     */
    private static void eliminarProducto() {
        Integer keyTienda = seleccionarTienda();

        if (keyTienda != null) {
            Tienda tienda = empresa.getTienda(keyTienda);
            if (tienda.listarProductos()) {
                String identificador = KeyboardReader.readString("Introduzca el identificador del producto: ",
                        "Error con el identificador introducido");

                while (tienda.getProducto(identificador) == null) {
                    System.out.println("El identificador no corresponde a ningún producto");
                    identificador = KeyboardReader.readString("Introduzca el identificador del producto: ",
                            "Error con el identificador introducido");
                }

                if (confirmarSiNo("¿Realmente quiere eliminar el producto? (S/N): ",
                        "Eliminación cancelada")) {
                    tienda.eliminarProducto(identificador);
                    saveJSON("empresa.json");
                }
            }
        }
    }

    /**
     * <p>Solicita los datos necesarios para crear un nuevo empleado y añadirlo a la tienda seleccionada.</p>
     */
    private static void anhadirEmpleado() {
        Integer keyTienda = seleccionarTienda();

        if (keyTienda != null) {
            String nombre = KeyboardReader.readString("Introduzca el nombre del empleado: ",
                    "Error con el nombre introducido");
            String apellidos = KeyboardReader.readString("Introduzca los apellidos del empleado: ",
                    "Error con los apellidos introducidos");
            empresa.getTienda(keyTienda).anhadirEmpleado(nombre, apellidos);
            saveJSON("empresa.json");
        }
    }

    /**
     * <p>Usa el método <b>listarEmpleados</b> de la clase <b>Tienda</b> para mostrar por pantalla los empleadosd de una
     * tienda determinada.</p>
     */
    private static void listarEmpleados() {
        Integer keyTienda = seleccionarTienda();

        if (keyTienda != null) {
            empresa.getTienda(keyTienda).listarEmpleados();
        }
    }

    /**
     * <p>Permite seleccionar una tienda determinada y, tras mostrar los empleados de dicha tienda, permite seleccionar
     * el que queramos eliminar.</p>
     */
    private static void eliminarEmpleado() {
        Integer keyTienda = seleccionarTienda();

        if (keyTienda != null) {
            Tienda tienda = empresa.getTienda(keyTienda);
            if (tienda.listarEmpleados()) {
                Integer keyEmpleado = KeyboardReader.readInt("Introduzca el número de empleado (0 para salir): ",
                        "Error con el número introducido");

                if (keyEmpleado != 0) {
                    while (tienda.getEmpleado(keyEmpleado) == null) {
                        System.out.println("El código no corresponde a ningún empleado");
                        keyEmpleado = KeyboardReader.readInt("Introduzca el número de empleado (0 para salir): ",
                                "Error con el número introducido");

                        if (keyEmpleado == 0) {
                            break;
                        }
                    }

                    if (keyEmpleado != 0 && confirmarSiNo("¿Realmente quiere eliminar el empleado? (S/N): ",
                            "Eliminación cancelada")) {
                        tienda.eliminarEmpleado(keyEmpleado);
                        saveJSON("empresa.json");
                    }
                }
            }
        }
    }

    /**
     * <p>Solicita los datos necesarios para crear un nuevo cliente y almacenarlo.</p>
     */
    private static void anhadirCliente() {
        System.out.println();
        String nombre = KeyboardReader.readString("Introduzca el nombre del cliente: ",
                "Error con el nombre introducido");
        String apellidos = KeyboardReader.readString("Introduzca los apellidos del cliente: ",
                "Error con los apellidos introducidos");
        String email = KeyboardReader.readString("Introduzca el e-mail del cliente: ",
                "Error con el e-mail introducido");
        empresa.anhadirCliente(nombre, apellidos, email);
        saveJSON("empresa.json");
    }

    /**
     * <p>Utiliza el método <b>listarClientes</b> de la clase <b>Empresa</b> para mostrar todos los clientes existentes
     * por pantalla.</p>
     */
    private static void listarClientes() {
        empresa.listarClientes();
    }

    /**
     * <p>Tras mostrar los clientes de la empresa, permite seleccionar el que queramos eliminar.</p>
     */
    private static void eliminarCliente() {
        Integer keyCliente = seleccionarCliente();

        if (keyCliente != null && confirmarSiNo("¿Realmente quiere eliminar el cliente? (S/N): ",
                "Eliminación cancelada")) {
            empresa.eliminarCliente(keyCliente);
            saveJSON("empresa.json");
        }
    }

    /**
     * <p>Descarga el contenido del feed RSS de El País a una cadena de texto. Usando la librería Sax, se analiza dicha
     * cadena y se muestran los titulares de las noticias.</p>
     */
    private static void titulares() {
        // En primer lugar se descarga el contenido del feed RSS.
        try (Scanner scanner = new Scanner(new URL("http://ep00.epimg.net/rss/elpais/portada.xml").openStream(),
                StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A");
            String noticiasXML = scanner.hasNext() ? scanner.next() : "";

            // Si se ha obtenido una cadena no vacía...
            if (!noticiasXML.equals("")) {
                SAXParserFactory factory = SAXParserFactory.newInstance();

                try {
                    SAXParser parser = factory.newSAXParser();

                    // El handler se encargará de buscar todos los elementos de tipo "title" que se encuentren.
                    DefaultHandler handler = new DefaultHandler() {
                        boolean title = false;

                        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                            if (qName.equalsIgnoreCase("title")) {
                                title = true;
                            }
                        }

                        public void characters(char ch[], int start, int length) throws SAXException {
                            if (title) {
                                String title = new String(ch, start, length);
                                System.out.println(title);
                                this.title = false;
                            }
                        }
                    };

                    // Parseamos la cadena del feed y el handler se encargará de mostrar el contenido de los titulares.
                    parser.parse(new InputSource(new StringReader(noticiasXML)), handler);
                } catch (SAXException e) {
                    System.err.println(e.toString());
                } catch (ParserConfigurationException e) {
                    System.err.println(e.toString());
                }
            } else {
                System.out.println();
                System.out.println("No se han podido obtener los titulares");
            }
        } catch (MalformedURLException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }


    /* MÉTODOS AUXILIARES */

    /**
     * <p>Muestra un listado con las tiendas existentes y permite seleccionar una de ellas. El método se asegura de que
     * no se pueda seleccionar una tienda inexistente.</p>
     *
     * @return La clave de la tienda seleccionada, o null en caso de haber salido sin seleccionar nada.
     */
    private static Integer seleccionarTienda() {
        if (empresa.listarTiendas()) {
            Integer keyTienda = KeyboardReader.readInt("Introduzca el número de la tienda (0 para salir): ",
                    "Error con el número introducido");

            if (keyTienda == 0) {
                return null;
            } else {
                while (empresa.getTienda(keyTienda) == null) { // Solo se permiten tiendas que existan en la empresa.
                    System.out.println("El código no corresponde a ninguna tienda");
                    keyTienda = KeyboardReader.readInt("Introduzca el número de la tienda (0 para salir): ",
                            "Error con el número introducido");

                    if (keyTienda == 0) {
                        return null;
                    }
                }

                return keyTienda;
            }
        } else {
            return null;
        }
    }

    /**
     * <p>Muestra un listado con los clientes existentes y permite seleccionar uno de ellos. El método se asegura de que
     * no se pueda seleccionar un cliente inexistente.</p>
     *
     * @return La clave del cliente seleccionado, o null en caso de haber salido sin seleccionar nada.
     */
    private static Integer seleccionarCliente() {
        if (empresa.listarClientes()) {
            Integer keyCliente = KeyboardReader.readInt("Introduzca el número del cliente (0 para salir): ",
                    "Error con el número introducido");

            if (keyCliente == 0) {
                return null;
            } else {
                while (empresa.getCliente(keyCliente) == null) { // Solo se permiten clientes que existan en la empresa.
                    System.out.println("El código no corresponde a ningún cliente");
                    keyCliente = KeyboardReader.readInt("Introduzca el número del cliente (0 para salir): ",
                            "Error con el número introducido");

                    if (keyCliente == 0) {
                        return null;
                    }
                }

                return keyCliente;
            }
        } else {
            return null;
        }
    }

    /**
     * <p>Método para mostrar al usuario preguntas que se puedan responder con sí o no.</p>
     *
     * @param pregunta Texto que se mostrará al usuario a modo de pregunta.
     * @param noMsg Texto que se mostrará en caso de seleccionar la opción del no.
     * @return true si se responde sí, false si se responde no.
     */
    private static boolean confirmarSiNo(String pregunta, String noMsg) {
        String seguro = KeyboardReader.readPattern(pregunta, "Opción no reconocida", "[SsNn]");

        if (seguro.equals("S") || seguro.equals("s")) {
            return true;
        } else {
            System.out.println(noMsg);
            return false;
        }
    }

    /**
     * <p>Utilizando la librería <b>GSON</b> se recuperan los datos de la empresa si ya hubiesen sido guardados con anterioridad.</p>
     * <p>Si no existe el fichero <b>json</b> o está totalmente vacío, se crea un nuevo objeto de la clase <b>Empresa</b>.</p>
     * <p>Si por el motivo que sea, el fichero <b>json</b> existe pero no es válido, se da opción al usuario de sobreescribirlo
     * con datos nuevos. Si cancela el proceso en este punto, el programa finaliza.</p>
     */
    private static void loadJSON() {
        Gson gson = new Gson();

        try {
            JsonReader jr = new JsonReader(new FileReader("empresa.json"));
            empresa = gson.fromJson(jr, Empresa.class);

            if (empresa == null) {
                empresa = new Empresa();
                saveJSON("empresa.json");
            }
        } catch(FileNotFoundException e) {
            empresa = new Empresa();
            saveJSON("empresa.json");
        } catch(JsonSyntaxException e) {
            System.out.println("El fichero JSON parece no ser válido.");
            if (confirmarSiNo("¿Desea seguir adelante (el fichero se sobreescribirá)? (S/N): ",
                    "Programa terminado")) {
                empresa = new Empresa();
                saveJSON("empresa.json");
            } else {
                System.exit(1);
            }
        }
    }

    /**
     * <p>Toma el objeto <b>empresa</b> y guarda su contenido en formato <b>json</b> con la librería <b>GSON</b>.</p>
     * <p>Al poder proporcionar el nombre del fichero generado, este método se usa tanto en los guardados genéricos
     * como también con la opción de menú que realiza las copias de seguridad.</p>
     *
     * @param filename Permite proporcionar el nombre de fichero que será generado.
     */
    private static void saveJSON(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            PrintWriter pw = new PrintWriter(filename);
            pw.println(gson.toJson(empresa));
            pw.close();
        } catch(FileNotFoundException e) {
            System.err.println(e.toString());
        }
    }
}
