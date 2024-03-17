import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        LispInterpreter interpreter = new LispInterpreter();

        // Define una función personalizada
        ArrayList<String> customFunction = new ArrayList<>();
        customFunction.add("*");
        customFunction.add("a");
        customFunction.add("b");
        interpreter.setDEFUN("MULTIPLICACION", customFunction);

        // Llama a la función definida
        ArrayList<String> expression = new ArrayList<>();
        expression.add("MULTIPLICACION");
        expression.add("2");
        expression.add("3");
        try {
            Object result = interpreter.getDEFUN("MULTIPLICACION");
            System.out.println("Resultado de la función MULTIPLICACION: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
