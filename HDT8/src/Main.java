import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; 

public class Main {
    public static void main(String[] args) {
        String filePathTxt = "procesos.txt";

        ArrayList<ArrayList<String>> text = txtReader(filePathTxt);
        
        for (ArrayList<String> line : text) {
            for (String word : line) {
                System.out.println(word);
            }
        }
    }
    
    public static String priority(ArrayList<ArrayList<String>> text) {
        String translatedText = "";
        for (ArrayList<String> line : text) {
            for (String word : line) {
                
            }
            translatedText += "\n";
        }
        return translatedText;
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
