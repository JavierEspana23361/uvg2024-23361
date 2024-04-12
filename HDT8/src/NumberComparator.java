import java.util.ArrayList;
import java.util.Comparator;

//Comparador para la version con priorityqueue

/**
 * Un comparador para comparar ArrayLists de Strings basado en el valor entero del elemento en el índice 3.
 */

public class NumberComparator implements Comparator<ArrayList<String>> {
    @Override
    public int compare(ArrayList<String> o1, ArrayList<String> o2) {
        int pr1 = Integer.parseInt(o1.get(3));
        int pr2 = Integer.parseInt(o2.get(3));
        return Integer.compare(pr1, pr2);
    }
}