import java.util.Comparator;

public class LanguageComparator<K extends Comparable<K>> implements Comparator<K>{

    /**
        * Compara dos objetos de tipo K y devuelve un entero que indica su orden relativo.
        * 
        * @param o1 el primer objeto a comparar
        * @param o2 el segundo objeto a comparar
        * @return un entero negativo si o1 es menor que o2, cero si son iguales, o un entero positivo si o1 es mayor que o2
        */
    @Override
    public int compare(K o1, K o2) {
        return o1.compareTo(o2);
    }
    
}
