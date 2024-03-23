import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.AbstractMap;

public class MapFactory<K, V> {
    public static final int HASHMAP = 1;
    public static final int TREEMAP = 2;
    public static final int LINKEDHASHMAP = 3;
    
    public AbstractMap<K, V> getMapInstance(int option) {
        switch (option) {
            case HASHMAP:
                return new HashMap<>();
                return new HashMap<K, V>();
            case TREEMAP:
                return new TreeMap<>();
                return new TreeMap<K, V>();
            case LINKEDHASHMAP:
                return new LinkedHashMap<>();
                return new LinkedHashMap<K, V>();
            default:
                throw new IllegalArgumentException("Tipo de mapa no válido: " + type);
                return new HashMap<K, V>();
        }
    }
}