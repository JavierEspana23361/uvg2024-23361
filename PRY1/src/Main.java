public class Main {
    
    public static void main(String[] args) {
        int f = 180;

        LispInterpreter interpreter = new LispInterpreter();

                // Convertir grados Fahrenheit a grados Celsius
                double c = (f - 32) * 5 / 9;
                System.out.println(f + " degrees Fahrenheit is equal to " + c + " degrees Celsius.");
    }
}
