import java.util.ArrayList;

/**
 * Esta interfaz representa una calculadora que realiza operaciones matemáticas básicas.
 */
public interface ICalculator {
    /**
     * Suma dos números enteros.
     * 
     * @param n1 el primer número entero
     * @param n2 el segundo número entero
     * @return la suma de los dos números
     */
    public int add(int n1, int n2);

    /**
     * Resta dos números enteros.
     * 
     * @param n1 el primer número entero
     * @param n2 el segundo número entero
     * @return la resta de los dos números
     */
    public int substract(int n1, int n2);

    /**
     * Multiplica dos números enteros.
     * 
     * @param n1 el primer número entero
     * @param n2 el segundo número entero
     * @return el producto de los dos números
     */
    public int multiplication(int n1, int n2);

    /**
     * Divide dos números enteros.
     * 
     * @param n1 el primer número entero (dividendo)
     * @param n2 el segundo número entero (divisor)
     * @return el cociente de la división
     * @throws ArithmeticException si el divisor es cero
     */
    public int division(int n1, int n2) throws ArithmeticException;

    /**
     * Calcula el residuo de la división entre dos números enteros.
     * 
     * @param n1 el primer número entero (dividendo)
     * @param n2 el segundo número entero (divisor)
     * @return el residuo de la división
     * @throws ArithmeticException si el divisor es cero
     */
    public int residue(int n1, int n2) throws ArithmeticException;

    /**
     * Lee un archivo y devuelve su contenido como una lista de cadenas.
     * 
     * @param file la ruta del archivo a leer
     * @return una lista de cadenas que representa el contenido del archivo
     * @throws Exception si ocurre un error al leer el archivo
     */
    public ArrayList<String> read(String file) throws Exception;

    /**
     * Resuelve una lista de elementos matemáticos y devuelve el resultado.
     * 
     * @param elements una lista de elementos matemáticos en formato de cadenas
     * @return el resultado de la operación matemática
     * @throws Exception si ocurre un error al resolver la lista de elementos
     */
    public int solve(ArrayList<String> elements) throws Exception;
}
    
        
