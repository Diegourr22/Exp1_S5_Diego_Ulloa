import java.util.ArrayList;
import java.util.Scanner;

public class Exp1_S5_Diego_Ulloa {

    static int totalEntradasVendidas = 0;
    static double totalIngresos = 0;
    static int entradasConDescuento = 0;
    static ArrayList<Entrada> listaEntradas = new ArrayList<>();
    static int contadorEntradas = 1;

    static class Entrada {
        int numero;
        String ubicacion;
        String tipoCliente;
        double precioFinal;

        public Entrada(int numero, String ubicacion, String tipoCliente, double precioFinal) {
            this.numero = numero;
            this.ubicacion = ubicacion;
            this.tipoCliente = tipoCliente;
            this.precioFinal = precioFinal;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String nombreTeatro = "Teatro Moro";
        int capacidadSala = 100;
        int entradasDisponibles = capacidadSala;
        double precioBase = 10000;

        boolean salir = false;

        while (!salir) {
            mostrarMenu(nombreTeatro);
            int opcion = obtenerNumero(scanner);

            switch (opcion) {
                case 1:
                    if (entradasDisponibles <= 0) {
                        System.out.println("No hay más entradas disponibles.");
                    } else {
                        entradasDisponibles = venderEntrada(scanner, precioBase, entradasDisponibles);
                    }
                    break;
                case 2:
                    mostrarPromociones();
                    break;
                case 3:
                    buscarEntrada(scanner);
                    break;
                case 4:
                    entradasDisponibles = eliminarEntrada(scanner, entradasDisponibles);
                    break;
                case 5:
                    mostrarEstadisticas(entradasDisponibles);
                    break;
                case 6:
                    salir = true;
                    System.out.println("¡Gracias por ingresar al " + nombreTeatro + "!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }

    // === Métodos Auxiliares ===

    public static void mostrarMenu(String nombreTeatro) {
        System.out.println("\n--- Bienvenido al sistema de venta de entradas del " + nombreTeatro + " ---");
        System.out.println("1. Venta de entradas");
        System.out.println("2. Ver promociones");
        System.out.println("3. Buscar entradas");
        System.out.println("4. Eliminar entrada");
        System.out.println("5. Ver estadísticas");
        System.out.println("6. Salir");
        System.out.print("Selecciona una opción: ");
    }

    public static int venderEntrada(Scanner scanner, double precioBase, int disponibles) {
        System.out.print("Ingrese ubicación (VIP, Platea, General): ");
        String ubicacion = scanner.nextLine();

        System.out.print("¿Es estudiante? (si/no): ");
        String esEstudiante = scanner.nextLine().toLowerCase();

        System.out.print("¿Es de la tercera edad? (si/no): ");
        String esTerceraEdad = scanner.nextLine().toLowerCase();

        double descuento = 0.0;
        String tipoCliente = "normal";

        if (esEstudiante.equals("si")) {
            descuento = 0.10;
            tipoCliente = "estudiante";
            entradasConDescuento++;
        } else if (esTerceraEdad.equals("si")) {
            descuento = 0.15;
            tipoCliente = "tercera edad";
            entradasConDescuento++;
        }

        double precioFinal = precioBase - (precioBase * descuento);
        Entrada nuevaEntrada = new Entrada(contadorEntradas, ubicacion, tipoCliente, precioFinal);
        listaEntradas.add(nuevaEntrada);

        System.out.println("Entrada vendida exitosamente.");
        System.out.println("Número de entrada: " + contadorEntradas);
        System.out.println("Precio final: $" + precioFinal);

        contadorEntradas++;
        totalEntradasVendidas++;
        totalIngresos += precioFinal;
        return disponibles - 1;
    }

    public static void mostrarPromociones() {
        System.out.println("\n--- Promociones disponibles ---");
        System.out.println("- 10% de descuento para estudiantes.");
        System.out.println("- 15% de descuento para personas de la tercera edad.");
    }

    public static void buscarEntrada(Scanner scanner) {
        System.out.print("Buscar por (numero/ubicacion/tipo): ");
        String criterio = scanner.nextLine().toLowerCase();
        boolean encontrado = false;

        for (Entrada entrada : listaEntradas) {
            boolean coincide = false;

            if (criterio.equals("numero")) {
                int numero = obtenerNumero(scanner);
                coincide = entrada.numero == numero;
            } else if (criterio.equals("ubicacion")) {
                String ubic = obtenerTexto("Ubicación", scanner);
                coincide = entrada.ubicacion.equalsIgnoreCase(ubic);
            } else if (criterio.equals("tipo")) {
                String tipo = obtenerTexto("Tipo (estudiante/tercera edad/normal)", scanner);
                coincide = entrada.tipoCliente.equalsIgnoreCase(tipo);
            }

            if (coincide) {
                System.out.println("Entrada encontrada:");
                System.out.println("Número: " + entrada.numero + ", Ubicación: " + entrada.ubicacion +
                        ", Tipo: " + entrada.tipoCliente + ", Precio: $" + entrada.precioFinal);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron coincidencias.");
        }
    }

    public static int eliminarEntrada(Scanner scanner, int disponibles) {
        int numEliminar = obtenerNumero(scanner);
        boolean eliminada = false;

        for (int i = 0; i < listaEntradas.size(); i++) {
            if (listaEntradas.get(i).numero == numEliminar) {
                System.out.println("Entrada eliminada con éxito.");
                totalIngresos -= listaEntradas.get(i).precioFinal;
                listaEntradas.remove(i);
                totalEntradasVendidas--;
                eliminada = true;
                disponibles++;
                break;
            }
        }

        if (!eliminada) {
            System.out.println("No se encontró la entrada.");
        }

        return disponibles;
    }

    public static void mostrarEstadisticas(int disponibles) {
        System.out.println("\n--- Estadísticas ---");
        System.out.println("Total entradas vendidas: " + totalEntradasVendidas);
        System.out.println("Total ingresos: $" + totalIngresos);
        System.out.println("Entradas con descuento: " + entradasConDescuento);
        System.out.println("Entradas disponibles: " + disponibles);
    }

    public static String obtenerTexto(String mensaje, Scanner scanner) {
        String texto = "";
        do {
            System.out.print(mensaje + ": ");
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El texto no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    public static int obtenerNumero(Scanner scanner) {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            if (scanner.hasNextInt()) {
                numero = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer
                valido = true;
            } else {
                System.out.println("Por favor ingrese un número válido.");
                scanner.nextLine(); // limpiar entrada inválida
            }
        }

        return numero;
    }
}
