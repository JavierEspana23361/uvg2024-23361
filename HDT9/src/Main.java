import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * The Main class is the entry point of the program. It provides functionality to compress and decompress text files using Huffman coding.
 * The program prompts the user to choose between compression and decompression, and performs the corresponding operation based on the user's input.
 * Compressed and decompressed files are saved in the specified file paths.
 */
public class Main {

    public static void main(String[] args) {
        String archivoArbol = "resources/arbol.tree";
        String archivoSalida = "resources/texto.huff";

        String archivoEntrada = "resources/textoOriginal.txt";
        String archivoComprimido = "resources/textoComprimido.txt";
        String archivoDescomprimido = "resources/textoDescomprimido.txt";

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("¿Desea comprimir (c) o descomprimir (d)?");
            String opcion = scanner.nextLine();
            String textoOriginal = leerArchivo(archivoEntrada);
            Huffman huffman = new Huffman(textoOriginal);
            String textoComprimido = huffman.comprimir();
            String textoDescomprimido = "";

            byte bytes[] = new byte[textoComprimido.length() / 8];
            for (int i = 0; i < textoComprimido.length(); i += 8) {
                String byteString = textoComprimido.substring(i, i + 8);
                bytes[i / 8] = (byte) Integer.parseInt(byteString, 2);
            }

            if (opcion.equalsIgnoreCase("c")) {
                System.out.println("Texto comprimido: " + textoComprimido);
                huffman.printCodes();

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(archivoComprimido);
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (FileOutputStream fos = new FileOutputStream(archivoSalida)) {
                    for (byte b : bytes) {
                        fos.write(b);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    File fileArbol = new File(archivoArbol);
                    fileArbol.createNewFile();
                    FileWriter writerArbol = new FileWriter(fileArbol);
                    writerArbol.write(huffman.getArbol().toString());
                    writerArbol.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (opcion.equalsIgnoreCase("d")) {
                try {
                    // Leer los bytes del archivo
                    FileInputStream fileInputStream = new FileInputStream(archivoComprimido);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int byteRead;
                    while ((byteRead = fileInputStream.read()) != -1) {
                        byteArrayOutputStream.write(byteRead);
                    }
                    fileInputStream.close();
        
                    // Convertir los bytes a un array
                    byte[] bytesaux = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
        
                    StringBuilder binaryText = new StringBuilder();
                    for (byte b : bytesaux) {
                        for (int i = 7; i >= 0; i--) {
                            binaryText.append((b >> i) & 1);
                        }
                    }
                    textoDescomprimido = binaryText.toString();
                } catch (IOException e) {
                    System.out.println("Error al leer el archivo.");
                    e.printStackTrace();
                }
                String textoDecodificado = huffman.descomprimir(textoDescomprimido);
                System.out.println("Texto descomprimido: " + textoDecodificado);

                try {
                    File file = new File(archivoDescomprimido);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    writer.write(textoDecodificado);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Opción no válida. Debe ingresar 'c' para comprimir o 'd' para descomprimir.");
            }
        }
    }

    /**
     * Reads the contents of a file and returns it as a string.
     *
     * @param archivo The path of the file to be read.
     * @return The contents of the file as a string.
     */
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
