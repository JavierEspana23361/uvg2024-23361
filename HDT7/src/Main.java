import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        dictionary.readDictionary();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecciona el lenguaje al que deseas traducir:");
        System.out.println("1. Inglés");
        System.out.println("2. Español");
        System.out.println("3. Francés");
        int language = scanner.nextInt();
        dictionary.translateWords(language);
        scanner.close();
    }
}
