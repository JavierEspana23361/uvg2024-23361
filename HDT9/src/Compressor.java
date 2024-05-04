import java.io.*;
import java.util.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;


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
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoEntrada));
            BitOutputStream bitOutputStream = new BitOutputStream(new BufferedOutputStream(new FileOutputStream(archivoSalida)))) {
            int caracter;
            while ((caracter = reader.read()) != -1) {
                String codigo = codigos.get((char) caracter);
                for (char bit : codigo.toCharArray()) {
                    bitOutputStream.writeBit(bit == '1');
                }
            }
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
            codigos.put(nodo.getChar(), codigo);
        } else {
            generarCodigosHuffman(nodo.getLeft(), codigo + "0", codigos);
            generarCodigosHuffman(nodo.getRight(), codigo + "1", codigos);
        }
    }

    public static void main(String[] args) {
        try {
            String archivoEntrada = "texto.txt";
            String archivoArbol = "arbol.tree";
            String archivoSalida = "texto.huff";

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
