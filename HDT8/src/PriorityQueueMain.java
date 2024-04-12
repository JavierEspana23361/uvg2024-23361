import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * La clase PriorityQueueMain es la versión del código que emplea PriorityQueue de Java Collections Framework.
 * Lee un archivo de texto, realiza una clasificación de prioridad en el texto y muestra el texto ordenado.
 */
public class PriorityQueueMain {
    /**
        * El método main es el punto de entrada del programa.
        * Lee un archivo de texto, realiza una clasificación de prioridad en el texto y muestra el texto ordenado.
        *
        * @param args Los argumentos de línea de comandos pasados al programa.
        */
   
    public static void main(String[] args) {
        String filePathTxt = "./src/procesos.txt";

        ArrayList<ArrayList<String>> text = txtReader(filePathTxt);
        
        text = priority(text);

        Comparator<ArrayList<String>> comparator = new NumberComparator(); 

        PriorityQueue<ArrayList<String>> heap = new PriorityQueue<>(comparator);

        for (ArrayList<String> line : text) {
            heap.offer(line);
        }

        while (!heap.isEmpty()) {
            ArrayList<String> line = heap.poll();
            for (String word : line) {
                if (word.equals(line.get(3))) {
                    line.set(3, "PR = " + word);
                    System.out.print(line.get(3) + " ");
                } else {
                    System.out.print(word + ", ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Calcula la prioridad de cada elemento en una lista de listas de cadenas.
     * La prioridad se calcula sumando 120 al valor numérico del tercer elemento de cada lista.
     * 
     * @param text la lista de listas de cadenas en la que se calculará la prioridad
     * @return la lista de listas de cadenas con la prioridad calculada
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
        * Cada línea del archivo se divide por comas y se agrega a una lista.
        * Luego, cada lista se agrega a una lista principal.
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


