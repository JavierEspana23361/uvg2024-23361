import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; 

public class Main {
    /**
     * El método principal del programa.
     * Lee un archivo de texto, procesa los datos y los muestra en orden de prioridad.
     * 
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        String filePathTxt = "./src/procesos.txt";

        ArrayList<ArrayList<String>> text = txtReader(filePathTxt);
        
        text = priority(text);

        HeapUsingIterativeBinaryTree<Integer, ArrayList<String>> heap = new HeapUsingIterativeBinaryTree<Integer, ArrayList<String>>(new ComparadorNumeros<>());

        for (ArrayList<String> line : text) {
            heap.Insert(Integer.parseInt(line.get(3)), line);
        }

        while (heap.count() > 0) {
            ArrayList<String> line = heap.get();
            for (String word : line) {
                if (word == line.get(3)) {
                    line.set(3, "PR = " + word);
                    System.out.print(line.get(3) + " ");
                } else {
                    System.out.print(word + ", ");
                }
            }
            System.out.println();
            heap.remove();
        }
    }
    
    /**
        * Añade una prioridad a cada elemento en la posición 2 de cada línea de texto.
        * La prioridad se calcula sumando 120 al valor actual en la posición 2.
        * 
        * @param text una lista de listas de cadenas de texto
        * @return la lista de listas de cadenas de texto con la prioridad añadida
        */
    public static ArrayList<ArrayList<String>> priority(ArrayList<ArrayList<String>> text) {
        for (ArrayList<String> line : text) {
            for (int i = 0; i < line.size(); i++) {
                if (i == 2) {
                    line.add(120 + Integer.parseInt(line.get(i)) + "");
                }
            }
        }
        return text;
    }

    /**
     * Lee un archivo de texto y devuelve una lista de listas de cadenas.
     * Cada lista interna representa una línea del archivo y contiene las cadenas separadas por comas.
     *
     * @param filePath la ruta del archivo de texto a leer
     * @return una lista de listas de cadenas que representa el contenido del archivo de texto
     */
    public static ArrayList<ArrayList<String>> txtReader(String filePath) {
        ArrayList<ArrayList<String>> linesArrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] linesList = line.split(",");
                ArrayList<String> lines = new ArrayList<>();
                for (int i = 0; i < linesList.length; i++) {
                    lines.add(linesList[i]);
                }
                linesArrayList.add(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return linesArrayList;
    }
}
