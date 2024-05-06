import java.io.*;
import java.util.*;

public class Descompressor {

    private static Node leerArbol(String archivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Node) inputStream.readObject();
        }
    }

    private static Map<String, Character> leerCodigos(String archivoArbol) throws IOException {
        Map<String, Character> codigos = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivoArbol))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\t");
                codigos.put(partes[1], partes[0].charAt(0));
            }
        }

        return codigos;
    }

    public static void descomprimir(String archivoCodigo, String archivoArbol, String archivoSalida) throws IOException, ClassNotFoundException {
        Node raiz = leerArbol(archivoArbol);

        Map<String, Character> codigos = leerCodigos(archivoCodigo);

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(archivoSalida));
        BitInputStream bitInputStream = new BitInputStream(new BufferedInputStream(new FileInputStream(archivoCodigo)));

        try {
            Node nodoActual = raiz;
            int bit;
            while ((bit = bitInputStream.readBit()) != -1) {
                if (bit == 0) {
                    nodoActual = nodoActual.getLeft();
                } else {
                    nodoActual = nodoActual.getRight();
                }

                if (nodoActual.isLeaf()) {
                    bufferedOutputStream.write(nodoActual.getCharacter());
                    nodoActual = raiz;
                }
            }
        } finally {
            // Cerrar los flujos manualmente
            bitInputStream.close();
            bufferedOutputStream.close();
        }
    }
}
