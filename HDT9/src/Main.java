import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Archivos sin uso aún
        String archivoArbol = "resources/arbol.tree";
        String archivoSalida = "resources/texto.huff";

        // Archivos de entrada
        String archivoEntrada = "resources/textoOriginal.txt";
        String archivoComprimido = "resources/textoComprimido.txt";

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("¿Desea comprimir (c) o descomprimir (d)?");
            String opcion = scanner.nextLine();
            String textoOriginal = leerArchivo(archivoEntrada);
            Huffman huffman = new Huffman(textoOriginal);
            String textoComprimido = huffman.comprimir();

            if (opcion.equalsIgnoreCase("c")) {
                System.out.println("Texto comprimido: " + textoComprimido);
                huffman.printCodes();

                try {
                    File file = new File(archivoComprimido);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    writer.write(textoComprimido);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (opcion.equalsIgnoreCase("d")) {
                textoOriginal = huffman.descomprimir(textoComprimido);
                System.out.println("Texto descomprimido: " + textoOriginal);
            } else {
                System.out.println("Opción no válida. Debe ingresar 'c' para comprimir o 'd' para descomprimir.");
            }
        }
    }

    public static String leerArchivo(String archivo) {
        String texto = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            int caracter;
            while ((caracter = reader.read()) != -1) {
                texto += (char) caracter;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return texto;
    }
}
