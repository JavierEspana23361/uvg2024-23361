import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapFactory {
    public enum MapType {
        HASHMAP,
        TREEMAP,
        LINKEDHASHMAP
    }

    public static Map<String, String> createMap(MapType type) {
        switch (type) {
            case HASHMAP:
                return new HashMap<>();
            case TREEMAP:
                return new TreeMap<>();
            case LINKEDHASHMAP:
                return new LinkedHashMap<>();
            default:
                throw new IllegalArgumentException("Tipo de mapa no válido: " + type);
        }
    }
}
