package app;


import excepciones.ClienteInvalidoException;
import excepciones.ClienteNoEncontradoException;
import java.util.List;
import java.util.Scanner;
import modelo.Cliente;

/** Menú de consola de CityLink: solo lectura por teclado y mensajes. */
public class App {
    private static final Scanner sc = new Scanner(System.in);
    private static final CityLink gestor = new CityLink();


public static void main(String[] args) {
    int opcion = 0;

    while (opcion != 10) {

        System.out.println("\n--- CITYLINK ---");
        System.out.println("1. Gestion de clientes");
        System.out.println("2. Gestion de repartidores");
        System.out.println("3. Registrar pedido");
        System.out.println("4. Atender pedido");
        System.out.println("5. Registrar entrega");
        System.out.println("6. Deshacer ultima operacion");
        System.out.println("7. Ordenar y listar pedidos");
        System.out.println("8. Buscar pedido");
        System.out.println("9. Intercalar pedidos");
        System.out.println("10. Salir");

        opcion = leerEntero("Ingrese una opcion: ");

        switch (opcion) {

            case 1 -> menuClientes();

            case 2 -> System.out.println("Gestion de repartidores.");

            case 3 -> System.out.println("Registrar pedido.");

            case 4 -> System.out.println("Atender pedido.");

            case 5 -> System.out.println("Registrar entrega.");

            case 6 -> System.out.println("Deshacer operacion.");

            case 7 -> System.out.println("Ordenar pedidos.");

            case 8 -> System.out.println("Buscar pedido.");

            case 9 -> System.out.println("Intercalar pedidos.");

            case 10 -> System.out.println("Hasta luego.");

            default -> System.out.println("Opcion incorrecta.");
        }
    }
}

    private static void menuClientes() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- Gestión de clientes ---");
            System.out.println("1. Alta de cliente");
            System.out.println("2. Baja de cliente");
            System.out.println("3. Buscar por código");
            System.out.println("4. Buscar por nombre");
            System.out.println("5. Listar clientes (ordenados por código)");
            System.out.println("0. Volver");
            opcion = leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> altaCliente();
                    case 2 -> bajaCliente();
                    case 3 -> System.out.println(gestor.buscarCliente(leerEntero("Código: ")));
                    case 4 -> buscarPorNombre();
                    case 5 -> listarClientes();
                    case 0 -> { }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (ClienteNoEncontradoException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (ClienteInvalidoException e) {
             System.out.println("Dato inválido: " + e.getMessage());
            } finally {
                System.out.println("(operación finalizada)");
            }
        }
    }

    private static void altaCliente() throws ClienteInvalidoException {
        int codigo = leerEntero("Código: ");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Teléfono: ");
        String tel = sc.nextLine();
        System.out.print("Zona: ");
        String zona = sc.nextLine();
        gestor.registrarCliente(new Cliente(codigo, nombre, tel, zona));
        System.out.println("Cliente registrado.");
    }

    private static void bajaCliente() throws ClienteNoEncontradoException {
        gestor.eliminarCliente(leerEntero("Código a eliminar: "));
        System.out.println("Cliente eliminado.");
    }

    private static void buscarPorNombre() throws ClienteNoEncontradoException {
        System.out.print("Nombre: ");
        System.out.println(gestor.buscarCliente(sc.nextLine()));
    }

    private static void listarClientes() {
        List<Cliente> lista = gestor.listarClientes();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Cliente c : lista) {
            System.out.println(c);
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido.");
            }
        }
    }
}