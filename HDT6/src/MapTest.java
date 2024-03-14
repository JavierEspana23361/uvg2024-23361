import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;

public class MapTest {

    @Test
    public void testHashMap() {
        Map<String, String> map = MapFactory.createMap(MapFactory.MapType.HASH_MAP);
        map.put("key1", "value1");
        map.put("key2", "value2");

        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
    }

    @Test
    public void testTreeMap() {
        Map<String, String> map = MapFactory.createMap(MapFactory.MapType.TREE_MAP);
        map.put("key1", "value1");
        map.put("key2", "value2");

        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
    }

    @Test
    public void testLinkedHashMap() {
        Map<String, String> map = MapFactory.createMap(MapFactory.MapType.LINKED_HASH_MAP);
        map.put("key1", "value1");
        map.put("key2", "value2");

        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
    }
}