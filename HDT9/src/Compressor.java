import java.io.*;
import java.util.*;

public class Compressor {

    private static int[] calcularFrecuencias(String archivo) throws IOException {
        int[] frecuencias = new int[256]; // 256 posibles caracteres ASCII

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            int caracter;
            while ((caracter = reader.read()) != -1) {
                frecuencias[caracter]++;
            }
        }

        return frecuencias;
    }

    private static void escribirArbol(Node raiz, String archivo) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(archivo))) {
            outputStream.writeObject(raiz);
        }
    }

    private static void escribirCodigoHuffman(Map<Character, String> codigos, String archivoEntrada, String archivoSalida) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(archivoSalida));
        BitOutputStream bitOutputStream = new BitOutputStream(bufferedOutputStream);

        try (BufferedReader reader = new BufferedReader(new FileReader(archivoEntrada))) {
            int caracter;
            while ((caracter = reader.read()) != -1) {
                String codigo = codigos.get((char) caracter);
                for (char bit : codigo.toCharArray()) {
                    bitOutputStream.writeBit(bit == '1');
                }
            }
        } finally {
            // Cerrar los flujos manualmente
            bitOutputStream.close();
            bufferedOutputStream.close();
        }
    }

    public static void comprimir(String archivoEntrada, String archivoArbol, String archivoSalida) throws IOException {
        int[] frecuencias = calcularFrecuencias(archivoEntrada);

        Tree arbol = new Tree(frecuencias);
        Node raiz = arbol.getRoot();

        escribirArbol(raiz, archivoArbol);

        Map<Character, String> codigos = new HashMap<>();
        generarCodigosHuffman(raiz, "", codigos);

        escribirCodigoHuffman(codigos, archivoEntrada, archivoSalida);
    }

    private static void generarCodigosHuffman(Node nodo, String codigo, Map<Character, String> codigos) {
        if (nodo.isLeaf()) {
            codigos.put(nodo.getCharacter(), codigo);
        } else {
            generarCodigosHuffman(nodo.getLeft(), codigo + "0", codigos);
            generarCodigosHuffman(nodo.getRight(), codigo + "1", codigos);
        }
    }

    public static void main(String[] args) {
        try {
            String archivoEntrada = "resources/texto.txt";
            String archivoArbol = " resources/arbol.tree";
            String archivoSalida = "resources/texto.huff";

            comprimir(archivoEntrada, archivoArbol, archivoSalida);

            File file = new File(archivoEntrada);
            long tamanoOriginal = file.length();
            file = new File(archivoSalida);
            long tamanoComprimido = file.length();

            double ratio = (double) tamanoOriginal / tamanoComprimido;
            System.out.println("Ratio de compresión: " + ratio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
