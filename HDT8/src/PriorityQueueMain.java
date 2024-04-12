import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueMain {
    public static void main(String[] args) {
        String filePathTxt = "./src/procesos.txt";

        ArrayList<ArrayList<String>> text = txtReader(filePathTxt);
        
        text = priority(text);

        Comparator<ArrayList<String>> comparator = new ComparadorNumeros(); // Remove the generic type argument <>

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

class ComparadorNumeros implements Comparator<ArrayList<String>> {
    @Override
    public int compare(ArrayList<String> o1, ArrayList<String> o2) {
        int pr1 = Integer.parseInt(o1.get(3));
        int pr2 = Integer.parseInt(o2.get(3));
        return Integer.compare(pr2, pr1);
    }
}
