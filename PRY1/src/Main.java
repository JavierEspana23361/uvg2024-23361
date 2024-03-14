import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LispInterpreter lisp = new LispInterpreter();
        
        while (true) {
            System.out.println("< ");
            String operation = sc.nextLine();
            try {
                ArrayList<String> tokens = lisp.tokenize(operation);
                System.out.println(lisp.eval(tokens));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("> ");
        }
    }
}
