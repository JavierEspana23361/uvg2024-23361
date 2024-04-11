import java.util.ArrayList;

public class PrintWalk implements IWalk<ArrayList<String>> {
    /**
        * Imprime el valor actual de la caminata.
        * 
        * @param actualValue el valor actual de la caminata
        */
    @Override
    public void doWalk(ArrayList<String> actualValue) {
        System.out.println(actualValue);
    }
}