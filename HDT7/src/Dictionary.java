
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.ArrayList;

public class Dictionary {
    private ITree<String, ArrayList<String>> englishTree;
    private ITree<String, ArrayList<String>> spanishTree;
    private ITree<String, ArrayList<String>> frenchTree;
    private ArrayList<String> Ewords;
    private ArrayList<String> Swords;
    private ArrayList<String> Fwords;

    /**
        * La clase Dictionary representa un diccionario que almacena palabras en diferentes idiomas.
        * Utiliza BinarySearchTree para almacenar palabras en inglés, español y francés.
        * La clase también proporciona métodos para agregar palabras y recuperar palabras del diccionario.
        */
    
    public Dictionary() {
        Comparator<String> compare = new LanguageComparator<>();
        englishTree = new BinarySearchTree<>(compare);
        spanishTree = new BinarySearchTree<>(compare);
        frenchTree = new BinarySearchTree<>(compare);
        Ewords = new ArrayList<>();
        Swords = new ArrayList<>();
        Fwords = new ArrayList<>();
    }
   
    /**
        * Lee el contenido de un archivo y lo devuelve como una cadena.
        * 
        * @return El contenido del archivo como una cadena.
        */
   
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

    /**
     * Obtiene todas las palabras del archivo de texto.
     * 
     * @return una lista de todas las palabras encontradas en el archivo de texto.
     */
    public ArrayList<String> getWords() {
        String text = readfile();
        ArrayList<String> words = new ArrayList<String>();
        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] lineWords = line.split(" ");
            for (String word : lineWords) {
                words.add(word);
            }
        }
        return words;
    }

    /**
     * Lee el diccionario desde un archivo de texto y almacena las palabras y sus traducciones en los árboles y listas correspondientes.
     */
    public void readDictionary() {
        String fileName = "./src/diccionario.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                if (words.length == 3) {
                    String englishWord = words[0];
                    String spanishWord = words[1];
                    String frenchWord = words[2];
                    ArrayList<String> translationsE = new ArrayList<>();
                    translationsE.add(spanishWord);
                    translationsE.add(frenchWord);
                    ArrayList<String> translationsS = new ArrayList<>();
                    translationsS.add(englishWord);
                    translationsS.add(frenchWord);
                    ArrayList<String> translationsF = new ArrayList<>();
                    translationsF.add(englishWord);
                    translationsF.add(spanishWord);
                    englishTree.insert(englishWord, translationsE);
                    spanishTree.insert(spanishWord, translationsS);
                    frenchTree.insert(frenchWord, translationsF);
                    Ewords.add(englishWord);
                    Swords.add(spanishWord);
                    Fwords.add(frenchWord);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Traduce las palabras en función del idioma seleccionado.
     * 
     * @param language El idioma seleccionado.
     *                 1: Español
     *                 2: Inglés
     *                 3: Francés
     */
    public void translateWords(int language) {
        ArrayList<String> words = getWords();
        ITree<String, ArrayList<String>> tree;
        ArrayList<String> translations = new ArrayList<>();
        ArrayList<String> translated = new ArrayList<>();
        switch (language) {
            case 1:
                for (String word : words) {
                    int type = detectLanguage(word);
                    switch (type) {
                        case 0:
                            translated.add("\"" + word + "\" ");
                            break;
                        case 1:
                            translated.add(word);
                            break;
                        case 2:
                            tree = spanishTree;
                            translations = tree.find(word);
                            translated.add(translations.get(0));
                            break;
                        case 3:
                            tree = frenchTree;
                            translations = tree.find(word);
                            translated.add(translations.get(0));
                            break;
                    }
                }
                break;
            case 2:
                for (String word : words) {
                    int type = detectLanguage(word);
                    switch (type) {
                        case 0:
                            translated.add("\"" + word + "\" ");
                            break;
                        case 1:
                            tree = englishTree;
                            translations = tree.find(word);
                            translated.add(translations.get(0));
                            break;
                        case 2:
                            translated.add(word);
                            break;
                        case 3:
                            tree = frenchTree;
                            translations = tree.find(word);
                            translated.add(translations.get(1));
                            break;
                    }
                }
                break;
            case 3:
                for (String word : words) {
                    int type = detectLanguage(word);
                    switch (type) {
                        case 0:
                            translated.add("\"" + word + "\" ");
                            break;
                        case 1:
                            tree = englishTree;
                            translations = tree.find(word);
                            translated.add(translations.get(1));
                            break;
                        case 2:
                            tree = spanishTree;
                            translations = tree.find(word);
                            translated.add(translations.get(1));
                            break;
                        case 3:
                            translated.add(word);
                            break;
                    }
                }
                break;
            default:
                System.out.println("Invalid language selection");
                return;
        }
        System.out.println("Texto traducido: ");
        for (String word : translated) {
            System.out.print(word + " ");
        }
    }

    /**
     * Determina el idioma al que pertenece una palabra.
     * 
     * @param word la palabra a evaluar
     * @return un entero que representa el idioma de la palabra:
     *         - 1 si la palabra está en inglés
     *         - 2 si la palabra está en español
     *         - 3 si la palabra está en francés
     *         - 0 si la palabra no está en ninguno de los idiomas mencionados
     */
    public int detectLanguage(String word) {
        if (Ewords.contains(word)) {
            return 1;
        } else if (Swords.contains(word)) {
            return 2;
        } else if (Fwords.contains(word)) {
            return 3;
        } else {
            return 0;
        }
    }

}
  
  
    

    


