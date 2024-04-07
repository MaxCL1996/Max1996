import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GestionMoroApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<Integer> entradasVendidas = new HashSet<>();

        while (true) {
            // Mensaje de bienvenida y opciones principales
            System.out.println("Bienvenido a Gestion Moro, ¿qué desea hacer?");
            System.out.println("1 - Comprar entradas");
            System.out.println("2 - Ver Promos");
            System.out.println("3 - Anular entradas");
            System.out.println("4 - Buscar entradas");
            System.out.print("Opción: ");
            int opcionPrincipal = scanner.nextInt();

            // Manejar la opción principal seleccionada por el usuario
            switch (opcionPrincipal) {
                case 1 -> {
                    System.out.println("¿Es ud estudiante? (S/N)");
                    String esEstudiante = scanner.next().toUpperCase();
                    System.out.println("¿Es ud perteneciente a la 3ra edad? (S/N)");
                    String esTerceraEdad = scanner.next().toUpperCase();

                    System.out.println("Para comprar VIP ($5000) presione a");
                    System.out.println("Para comprar Platea ($3000) presione b");
                    System.out.println("Para comprar General ($1000) presione c");
                    System.out.print("Opción: ");
                    String tipoEntrada = scanner.next();

                    switch (tipoEntrada) {
                        case "a" -> ofrecerCompraPorNumeracion(1, 20, "VIP", scanner, entradasVendidas, esEstudiante, esTerceraEdad);
                        case "b" -> ofrecerCompraPorNumeracion(21, 50, "Platea", scanner, entradasVendidas, esEstudiante, esTerceraEdad);
                        case "c" -> ofrecerCompraPorNumeracion(51, 100, "General", scanner, entradasVendidas, esEstudiante, esTerceraEdad);
                        default -> System.out.println("Opción no válida.");
                    }
                }
                case 2 -> {
                    System.out.println("No tenemos promos disponibles");
                    break;
                }
                case 3 -> anularEntradas(entradasVendidas, scanner);
                case 4 -> buscarEntradas(scanner, entradasVendidas);
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    public static void ofrecerCompraPorNumeracion(int inicio, int fin, String tipo, Scanner scanner, Set<Integer> entradasVendidas, String esEstudiante, String esTerceraEdad) {
        double precio;
        switch (tipo) {
            case "VIP" -> precio = 5000;
            case "Platea" -> precio = 3000;
            case "General" -> precio = 1000;
            default -> {
                System.out.println("Tipo de entrada no válido.");
                return;
            }
        }

        if (esEstudiante.equals("S") && esTerceraEdad.equals("S")) {
            precio *= 0.85; // Aplicar descuento del 15% por ser de la tercera edad
        } else if (esEstudiante.equals("S")) {
            precio *= 0.9; // Aplicar descuento del 10% por ser estudiante
        } else if (esTerceraEdad.equals("S")) {
            precio *= 0.85; // Aplicar descuento del 15% por ser de la tercera edad
        }

        System.out.println("¿Cuántas desea comprar?");
        int cantidad = scanner.nextInt();
        System.out.println("Tenemos disponibles las siguientes entradas:");
        for (int i = inicio; i <= fin; i++) {
            if (!entradasVendidas.contains(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println("Escriba los números de las entradas que desea comprar separados por coma:");
        String entradaComprada = scanner.next();

        // Verificar si las entradas seleccionadas están disponibles
        boolean entradasDisponibles = true;
        String[] entradasSeleccionadas = entradaComprada.split(",");
        for (String entrada : entradasSeleccionadas) {
            int numeroEntrada = Integer.parseInt(entrada);
            if (numeroEntrada < inicio || numeroEntrada > fin || entradasVendidas.contains(numeroEntrada)) {
                entradasDisponibles = false;
                break;
            }
        }

        if (entradasDisponibles) {
            for (String entrada : entradasSeleccionadas) {
                int numeroEntrada = Integer.parseInt(entrada);
                entradasVendidas.add(numeroEntrada);
            }
            double total = cantidad * precio;
            System.out.println("Ha comprado " + cantidad + " entradas de " + tipo + " por un total de $" + total);
        } else {
            System.out.println("Una o más entradas seleccionadas no están disponibles.");
        }
    }

    public static void anularEntradas(Set<Integer> entradasVendidas, Scanner scanner) {
        System.out.println("Ingrese los números de las entradas que desea anular separados por coma:");
        String entradasAnular = scanner.next();
        String[] entradasSeleccionadas = entradasAnular.split(",");
        for (String entrada : entradasSeleccionadas) {
            int numeroEntrada = Integer.parseInt(entrada);
            if (entradasVendidas.contains(numeroEntrada)) {
                entradasVendidas.remove(numeroEntrada);
                System.out.println("Entrada " + numeroEntrada + " anulada correctamente.");
            } else {
                System.out.println("Entrada " + numeroEntrada + " no fue vendida anteriormente.");
            }
        }
    }

    public static void buscarEntradas(Scanner scanner, Set<Integer> entradasVendidas) {
        System.out.println("Seleccione una opción:");
        System.out.println("a) Buscar por número");
        System.out.println("b) Buscar por ubicación");
        System.out.print("Opción: ");
        String opcion = scanner.next();

        switch (opcion) {
            case "a" -> {
                System.out.println("Ingrese la entrada a consultar:");
                int numeroEntrada = scanner.nextInt();
                if (entradasVendidas.contains(numeroEntrada)) {
                    System.out.println("La entrada " + numeroEntrada + " fue vendida anteriormente.");
                } else {
                    System.out.println("La entrada " + numeroEntrada + " está disponible para la venta.");
                }
            }
            case "b" -> {
                System.out.println("Entradas vendidas:");
                for (int entrada : entradasVendidas) {
                    System.out.print(entrada + " ");
                }
                System.out.println();
                System.out.println("Entradas disponibles:");
                for (int i = 1; i <= 100; i++) {
                    if (!entradasVendidas.contains(i)) {
                        System.out.print(i + " ");
                    }
                }
                System.out.println();
            }
            default -> System.out.println("Opción no válida.");
        }
    }
}
