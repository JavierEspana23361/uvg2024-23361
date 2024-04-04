
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.ArrayList;
public class Dictionary {



    public String readfile() {
        String fileName = "./src/texto.txt";
        String text = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                text += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }


    public String translate() {
        String text = readfile();
        String fileName = "./src/diccionario.txt";
        BinarySearchTree<String, Association<String, String>> englishTree = new BinarySearchTree<>(new StringComparator());
        BinarySearchTree<String, Association<String, String>> spanishTree = new BinarySearchTree<>(new StringComparator());
        BinarySearchTree<String, Association<String, String>> frenchTree = new BinarySearchTree<>(new StringComparator());
        processDictionaryFile(fileName, englishTree, spanishTree, frenchTree);
        String translatedText = "";
        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                String translatedWord = translateWord(word.toLowerCase(), englishTree, spanishTree, frenchTree);
                translatedText += translatedWord + " ";
            }
            translatedText += "\n";
        }
        return translatedText;
    }

    private void processDictionaryFile(String fileName, 
        BinarySearchTree<String, Association<String, String>> englishTree,
        BinarySearchTree<String, Association<String, String>> spanishTree,
        BinarySearchTree<String, Association<String, String>> frenchTree) {
        
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Association<String, String> association = new Association<String, String>(parts[0], parts[1], parts[2]);
                    englishTree.insert(parts[0], association);
                    spanishTree.insert(parts[1], association);
                    frenchTree.insert(parts[2], association);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String translateWord(String word,
        BinarySearchTree<String, Association<String, String>> englishTree,
        BinarySearchTree<String, Association<String, String>> spanishTree,
        BinarySearchTree<String, Association<String, String>> frenchTree) {
            // ...

        if (word.matches("[a-zA-Z]+")) { // Check if the word contains only alphabets
            String language = detectLanguage(word, englishTree, spanishTree, frenchTree);
            switch (language) {
                case "English":
                    Association<String, String> englishAssociation = englishTree.find(word).getValue();
                    return englishAssociation.getSpanish();
                case "Spanish":
                    Association<String, String> spanishAssociation = spanishTree.find(word).getValue();
                    return spanishAssociation.getEnglish();
                case "French":
                    Association<String, String> frenchAssociation = frenchTree.find(word).getValue();
                    return frenchAssociation.getFrench();
                default:
                    return "*" + word + "*"; // Return the original word if it's not found in the dictionary
            }
        } else {
            return word; // Return the word as is if it contains non-alphabetic characters
        }
    }

    private String detectLanguage(String word,
        BinarySearchTree<String, Association<String, String>> englishTree,
        BinarySearchTree<String, Association<String, String>> spanishTree,
        BinarySearchTree<String, Association<String, String>> frenchTree) {
        if (englishTree.find(word) != null) {
            return "English";
        } else if (spanishTree.find(word) != null) {
            return "Spanish";
        } else if (frenchTree.find(word) != null) {
            return "French";
        } else {
            return "Unknown";
        }
    }
    /* 
    private static void processDictionaryFile(String fileName, 
        BinarySearchTree<String, Association<String, String>> englishTree,
        BinarySearchTree<String, Association<String, String>> spanishTree,
        BinarySearchTree<String, Association<String, String>> frenchTree) {
        
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Association<String, String> association = new Association<>(parts[0], parts[1], parts[2]);
                    englishTree.insert(parts[0], association);
                    spanishTree.insert(parts[1], association);
                    frenchTree.insert(parts[2], association);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processTextFile(String fileName, BinarySearchTree<String, Association<String, String>> englishTree,
        BinarySearchTree<String, Association<String, String>> spanishTree,
        BinarySearchTree<String, Association<String, String>> frenchTree) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    String translatedWord = translateWord(word.toLowerCase(), englishTree, spanishTree, frenchTree);
                    System.out.print(translatedWord + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String translateWord(String word,
        BinarySearchTree<String, Association<String, String>> englishTree,
        BinarySearchTree<String, Association<String, String>> spanishTree,
        BinarySearchTree<String, Association<String, String>> frenchTree) {
        if (word.matches("[a-zA-Z]+")) { // Check if the word contains only alphabets
            String language = detectLanguage(word, englishTree, spanishTree, frenchTree);
            switch (language) {
                case "English":
                    Association<String, String> englishAssociation = englishTree.find(word).getValue();
                    return englishAssociation.getSpanish();
                case "Spanish":
                    Association<String, String> spanishAssociation = spanishTree.find(word).getValue();
                    return spanishAssociation.getEnglish();
                case "French":
                    Association<String, String> frenchAssociation = frenchTree.find(word).getValue();
                    return frenchAssociation.getEnglish();
                default:
                    return "*" + word + "*"; // Return the original word if it's not found in the dictionary
            }
        } else {
            return word; // Return the word as is if it contains non-alphabetic characters
        }
    }

    private static String detectLanguage(String word,
        BinarySearchTree<String, Association<String, String>> englishTree,
        BinarySearchTree<String, Association<String, String>> spanishTree,
        BinarySearchTree<String, Association<String, String>> frenchTree) {
        if (englishTree.find(word) != null) {
            return "English";
        } else if (spanishTree.find(word) != null) {
            return "Spanish";
        } else if (frenchTree.find(word) != null) {
            return "French";
        } else {
            return "Unknown";
        }
    }*/
}

