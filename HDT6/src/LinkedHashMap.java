import java.util.List;

public class LinkedHashMap implements IMap{
    public void save(String key, String value) {
        System.out.println("Saving key: " + key + " value: " + value + " in a LinkedHashMap");
    }

    public String get(String key) {
        System.out.println("Getting value for key: " + key + " from a LinkedHashMap");
        return "value";
    }

    public void delete(String key) {
        System.out.println("Deleting key: " + key + " from a LinkedHashMap");
    }

    public void update(String key, String value) {
        System.out.println("Updating key: " + key + " with value: " + value + " in a LinkedHashMap");
    }

    public List<String> getAll() {
        System.out.println("Getting all values from a LinkedHashMap");
        return null;
    }
}
