import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LispInterpreter lisp = new LispInterpreter();
        

        // 180 Es la temperatura en grados farenheit, f es la cantidad de grados farenheit
        // String expression = "(* (/ (- f 32) 9) 5)";
        String expression = "(* (/ (- 180 32) 9) 5)";


        // Esta es la base para sacar la sucesión de fibonacci, n es el número buscado en suceción
        //String fibonacci = "(* (/ 1 (ROOT 5 2)) (- (EXP (/ (+ 1 (ROOT 5 2)) 2) n ) (EXP (/ (- 1 (ROOT 5 2)) 2) n ) ))";
        String fibonacci = "(* (/ 1 (ROOT 5 2)) (- (EXP (/ (+ 1 (ROOT 5 2)) 2) 7 ) (EXP (/ (- 1 (ROOT 5 2)) 2) 7 ) ))";
      
        try {
            // Tokeniza la expresión antes de evaluarla
            ArrayList<String> exptokens = lisp.tokenize(expression);
            ArrayList<String> fibtokens = lisp.tokenize(fibonacci);

            // Evalúa la expresión tokenizada
            System.out.println("Resultado de farenheit a celsius: " + lisp.eval(exptokens));
            System.out.println("Resultado de fibonacci: " + lisp.eval(fibtokens));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}
