import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.AbstractMap;

/**
 * The MapFactory class provides a factory method to create instances of different map implementations.
 * It supports HashMap, TreeMap, and LinkedHashMap.
 *
 * @param <K> the type of keys maintained by the map
 * @param <V> the type of mapped values
 */
public class MapFactory<K, V> {
    public static final int HASHMAP = 1;
    public static final int TREEMAP = 2;
    public static final int LINKEDHASHMAP = 3;

    /**
     * Returns an instance of the specified map implementation based on the given option.
     *
     * @param option the option representing the desired map implementation
     * @return an instance of the specified map implementation
     */
    public AbstractMap<K, V> getMapInstance(int option) {
        switch (option) {
            case HASHMAP:
                return new HashMap<K, V>();
            case TREEMAP:
                return new TreeMap<K, V>();
            case LINKEDHASHMAP:
                return new LinkedHashMap<K, V>();
            default:
                return new HashMap<K, V>();
        }
    }
}