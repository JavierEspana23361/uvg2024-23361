import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LispInterpreter lisp = new LispInterpreter();
        System.out.println("Ingrese la expresión a evaluar: ");
        // 180 Es la temperatura en grados farenheit
        String expression = "(* (/ (- 180 32) 9) 5)";

        try {
            // Tokeniza la expresión antes de evaluarla
            ArrayList<String> tokens = lisp.tokenize(expression);
            System.out.println("Tokens: " + tokens);

            // Evalúa la expresión tokenizada
            System.out.println("Resultado de farenheit a celsius: " + lisp.eval(tokens));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
