/**
 * La clase App representa el punto de entrada del programa.
 * Contiene el método main que inicializa el objeto Calculator,
 * lee datos desde un archivo y llama al método solve para realizar cálculos.
 */
public class App {
    
    /** 
     * El método main es el punto de entrada del programa.
     * Crea un objeto Calculator, lee datos desde un archivo
     * y llama al método solve para realizar cálculos.
     * 
     * @param args los argumentos de la línea de comandos
     */ 
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        try {
            calc.solve(calc.read("datos.txt"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}