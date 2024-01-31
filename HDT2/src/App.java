public class App {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        try {
            System.out.println(calc.solve(calc.read("datos.txt")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


