import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LispInterpreter lisp = new LispInterpreter();
        System.out.println("Ingrese la expresión a evaluar: ");
        //180 Es la temperatura en grados farenheit
        String expression = "(* (/ (- 180 32) 9) 5)";
        
        System.out.println("Resultado: " + lisp.tokenize(expression));
        try {
            System.out.println("Resultado de farenheit a celcious: " + lisp.eval(lisp.tokenize(expression)));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

          
    }
        
}

