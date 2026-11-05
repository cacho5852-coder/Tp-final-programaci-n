package app;

import java.util.Scanner;
import modelo.Cliente;
import excepciones.AlmacenamientoLlenoException;
import excepciones.ClienteNoEncontradoException;

public class App {
    private static final Scanner sc = new Scanner(System.in);
    private static final CityLink gestor = new CityLink();

    public static void main(String[] args) {
        int opcion = 0;
        while (opcion != 10) {
            System.out.println("\n=== CityLink ===");
            System.out.println("1. Registrar cliente");
            System.out.println("6. Buscar cliente");
            System.out.println("10. Salir");
            opcion = leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> registrarCliente();
                    case 6 -> buscarCliente();
                    case 10 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opción no disponible todavía.");
                }
            } catch (AlmacenamientoLlenoException | ClienteNoEncontradoException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Dato inválido: " + e.getMessage());
            } finally {
                System.out.println("(operación finalizada)");
            }
        }
    }

    private static void registrarCliente() throws AlmacenamientoLlenoException {
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

    private static void buscarCliente() throws ClienteNoEncontradoException {
        System.out.print("Buscar por (1) código o (2) nombre: ");
        String modo = sc.nextLine().trim();
        if (modo.equals("1")) {
            System.out.println(gestor.buscarCliente(leerEntero("Código: ")));
        } else {
            System.out.print("Nombre: ");
            System.out.println(gestor.buscarCliente(sc.nextLine()));
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