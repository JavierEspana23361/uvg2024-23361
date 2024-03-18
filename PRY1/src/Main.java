import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Entrada para probar el interprete con definir funciones defun: (DEFUN factorial (n) (COND ((EQUAL n 0) 1) (T (* n (factorial (- n 1))))))
        // Entrada para probar el interprete con ejecutar funciones defun: (factorial 5)
        Scanner sc = new Scanner(System.in);
        LispInterpreter lisp = new LispInterpreter();
        
        while (true) {
            System.out.println("< ");
            String operation = sc.nextLine();
            try {
                ArrayList<String> tokens = lisp.tokenize(operation);
                Object result = lisp.eval(tokens);
                System.out.println("Resultado: " + result);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("> ");
        }
    }
}