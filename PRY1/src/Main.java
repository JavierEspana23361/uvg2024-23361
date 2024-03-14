import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LispInterpreter lisp = new LispInterpreter();
        

        // 180 Es la temperatura en grados farenheit, f es la cantidad de grados farenheit
        // String expression = "(* (/ (- f 32) 9) 5)";
        String expression = "(+ 5(LIST 2 3 3 1))";


        // Esta es la base para sacar la sucesión de fibonacci, n es el número buscado en suceción
        //String fibonacci = "(* (/ 1 (ROOT 5 2)) (- (EXP (/ (+ 1 (ROOT 5 2)) 2) n ) (EXP (/ (- 1 (ROOT 5 2)) 2) n ) ))";
        String fibonacci = "(+ 4 (* 3 2 1) 7)";

        String condString = "(COND (EQUAL 4 4) (QUOTE hola) (QUOTE adios))";
      
        try {
            // Tokeniza la expresión antes de evaluarla
            ArrayList<String> condtokens = lisp.tokenize(condString);
            ArrayList<String> exptokens = lisp.tokenize(expression);
            ArrayList<String> fibtokens = lisp.tokenize(fibonacci);

            // Evalúa la expresión tokenizada
            System.out.println("Resultado de la condición: " + lisp.eval(condtokens));
            //System.out.println("Resultado de farenheit a celsius: " + lisp.eval(exptokens));
            //System.out.println("Resultado de fibonacci: " + lisp.eval(fibtokens));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}
