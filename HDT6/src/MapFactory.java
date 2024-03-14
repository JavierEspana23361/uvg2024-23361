import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;

public class MapFactory {
    public enum MapType {
        HASH_MAP,
        TREE_MAP,
        LINKED_HASH_MAP
    }

    public static Map<String, String> createMap(MapType type) {
        switch (type) {
            case HASH_MAP:
                return new HashMap<>();
            case TREE_MAP:
                return new TreeMap<>();
            case LINKED_HASH_MAP:
                return new LinkedHashMap<>();
            default:
                throw new IllegalArgumentException("Unsupported map type: " + type);
        }
    }

    // Implementación del MapFactory, ejemplo: Map<String, String> map = MapFactory.createMap(MapFactory.MapType.HASH_MAP);

}
