import java.util.ArrayList;

public interface ICalculator {
    public int add(int n1, int n2);
    public int substract(int n1, int n2);
    public int multiplication(int n1, int n2);
    public int division(int n1, int n2);
    public int resudue(int n1, int n2);
    public ArrayList<String> read(String file) throws Exception;
    public int solve(ArrayList<String> elements) throws Exception;
}
    
        
