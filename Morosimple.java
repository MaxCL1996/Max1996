import java.util.Scanner;

public class MoroSimpleApp {
    static int entradasVIPDisponibles = 20;
    static int entradasPlateaDisponibles = 30;
    static int entradasGeneralDisponibles = 50;
    static boolean[] estadoEntradas = new boolean[100]; // false = disponible, true = vendida

    public static void main(String[] args) {
        mostrarMensajeInicial();
    }

    public static void mostrarMensajeInicial() {
        System.out.println("BIENVENIDO A MORO SIMPLE");
        System.out.println("ES USTED ESTUDIANTE O DE LA TERCERA EDAD ?");
        System.out.println("1) ESTUDIANTE");
        System.out.println("2) TERCERA EDAD");
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        while (opcion != 1 && opcion != 2) {
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion != 1 && opcion != 2) {
                    System.out.println("Por favor, seleccione una opción válida (1 o 2).");
                }
            } else {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next();
            }
        }

        switch (opcion) {
            case 1:
                mostrarMenu(0.9); // Descuento del 10% para estudiantes
                break;
            case 2:
                mostrarMenu(0.85); // Descuento del 15% para tercera edad
                break;
        }
    }

    public static void mostrarMenu(double descuento) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Qué desea hacer?");
        System.out.println("1 - Comprar asientos");
        System.out.println("2 - Cerrar venta e imprimir boleta");
        System.out.println("3 - Más información");

        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                comprarAsientos(descuento);
                break;
            case 2:
                cerrarVentaEImprimirBoleta(descuento);
                break;
            case 3:
                mostrarInformacion();
                break;
            default:
                System.out.println("Opción no válida.");
                mostrarMenu(descuento);
        }
    }

    public static void mostrarEntradasDisponibles() {
        System.out.println("ASIENTOS VIP DISPONIBLES: " + entradasVIPDisponibles);
        System.out.println("ASIENTOS PLATEA DISPONIBLES: " + entradasPlateaDisponibles);
        System.out.println("ASIENTOS GENERAL DISPONIBLES: " + entradasGeneralDisponibles);
        System.out.println("Números disponibles:");
        for (int i = 1; i <= 100; i++) {
            if (!estadoEntradas[i - 1]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public static void comprarAsientos(double descuento) {
        mostrarEntradasDisponibles();
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Cuál o cuáles asientos desea comprar? (Ingrese los números separados por comas)");

        String asientosComprar = scanner.nextLine();
        if (!asientosComprar.matches("([1-9][0-9]?,)*[1-9][0-9]?")) {
            System.out.println("Entrada no válida. Asegúrese de usar solo números y comas.");
            comprarAsientos(descuento);
            return;
        }

        double totalPagar = calcularTotalPagar(asientosComprar, descuento);
        actualizarAsientosDisponibles(asientosComprar);
        System.out.println("¡Compra realizada con éxito!");
        System.out.println("Total a pagar (con descuento aplicado): $" + totalPagar);
        mostrarEntradasDisponibles();
        mostrarMenu(descuento);
    }

    public static double calcularTotalPagar(String asientosComprar, double descuento) {
        double totalSinDescuento = 0.0;
        String[] asientosSeleccionados = asientosComprar.split(",");
        for (String asiento : asientosSeleccionados) {
            int numeroAsiento = Integer.parseInt(asiento.trim());
            if (numeroAsiento >= 1 && numeroAsiento <= 20 && !estadoEntradas[numeroAsiento - 1]) {
                totalSinDescuento += 5000;
            } else if (numeroAsiento >= 21 && numeroAsiento <= 50 && !estadoEntradas[numeroAsiento - 1]) {
                totalSinDescuento += 3000;
            } else if (numeroAsiento >= 51 && numeroAsiento <= 100 && !estadoEntradas[numeroAsiento - 1]) {
                totalSinDescuento += 1000;
            }
        }

        // Aplicar descuento
        totalSinDescuento *= descuento;

        return totalSinDescuento;
    }

    public static void actualizarAsientosDisponibles(String asientosComprar) {
        String[] asientosSeleccionados = asientosComprar.split(",");
        for (String asiento : asientosSeleccionados) {
            int numeroAsiento = Integer.parseInt(asiento.trim());
            if (numeroAsiento >= 1 && numeroAsiento <= 20) {
                entradasVIPDisponibles--;
                estadoEntradas[numeroAsiento - 1] = true;
            } else if (numeroAsiento >= 21 && numeroAsiento <= 50) {
                entradasPlateaDisponibles--;
                estadoEntradas[numeroAsiento - 1] = true;
            } else if (numeroAsiento >= 51 && numeroAsiento <= 100) {
                entradasGeneralDisponibles--;
                estadoEntradas[numeroAsiento - 1] = true;
            }
        }
    }

    public static void cerrarVentaEImprimirBoleta(double descuento) {
        double totalPagar = calcularTotalVenta(descuento);
        if (totalPagar > 0) {
            System.out.println("BOLETA DE COMPRA:");
            System.out.println("Asientos comprados:");
            // Aquí se imprimirían los detalles de los asientos comprados si fuera necesario
            System.out.println("Total a pagar: $" + totalPagar);
        } else {
            System.out.println("No hay asientos comprados para imprimir una boleta.");
        }
        mostrarMensajeInicial();
    }

    public static double calcularTotalVenta(double descuento) {
        double total = 0.0;
        for (int i = 0; i < estadoEntradas.length; i++) {
            if (estadoEntradas[i]) {
                if (i < 20) {
                    total += 5000;
                } else if (i < 50) {
                    total += 3000;
                } else {
                    total += 1000;
                }
            }
        }

        // Aplicar descuento
        total *= descuento;

        return total;
    }

    public static void mostrarInformacion() {
        System.out.println("Modificar una venta existente - Eliminar una venta pospuestos por la administración. PRONTO DISPONIBLES.");
        mostrarMenu(1); // Se asume descuento del 10% por defecto para mostrar el menú
    }
}
