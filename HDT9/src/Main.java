import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Desea comprimir (c) o descomprimir (d)?");
        String opcion = scanner.nextLine();

        if (opcion.equalsIgnoreCase("c")) {
            comprimir();
        } else if (opcion.equalsIgnoreCase("d")) {
            descomprimir();
        } else {
            System.out.println("Opción no válida. Debe ingresar 'c' para comprimir o 'd' para descomprimir.");
        }
    }

    private static void comprimir() {
        try {
            String archivoEntrada = "resources/texto.txt";
            String archivoArbol = "resources/arbol.tree";
            String archivoSalida = "resources/texto.huff";

            Compressor.comprimir(archivoEntrada, archivoArbol, archivoSalida);

            File file = new File(archivoEntrada);
            long tamanoOriginal = file.length();
            file = new File(archivoSalida);
            long tamanoComprimido = file.length();

            double ratio = (double) tamanoOriginal / tamanoComprimido;
            System.out.println("Compresión completada.");
            System.out.println("Ratio de compresión: " + ratio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void descomprimir() {
        try {
            String archivoComprimido = "resources/texto.huff";
            String archivoArbol = "resources/arbol.tree";
            String archivoDescomprimido = "resources/texto_descomprimido.txt";
    
            Descompressor.descomprimir(archivoComprimido, archivoArbol, archivoDescomprimido);
    
            File file = new File(archivoComprimido);
            long tamanoComprimido = file.length();
            file = new File(archivoDescomprimido);
            long tamanoDescomprimido = file.length();
    
            double ratio = (double) tamanoDescomprimido / tamanoComprimido;
            System.out.println("Descompresión completada.");
            System.out.println("Ratio de compresión: " + ratio);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }    
}
