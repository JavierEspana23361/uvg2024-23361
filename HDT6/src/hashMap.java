import java.util.List;

public class hashMap implements IMap {
        
        public void save(String key, String value) {
            System.out.println("Saving key: " + key + " value: " + value + " in a hashMap");
        }
    
        public String get(String key) {
            System.out.println("Getting value for key: " + key + " from a hashMap");
            return "value";
        }
    
        public void delete(String key) {
            System.out.println("Deleting key: " + key + " from a hashMap");
        }
    
        public void update(String key, String value) {
            System.out.println("Updating key: " + key + " with value: " + value + " in a hashMap");
        }
    
        public List<String> getAll() {
            System.out.println("Getting all values from a hashMap");
            return null;
        }
}