import java.util.List;

public interface IMap {
        public void save(String key, String value);
        
        public String get(String key);
        
        public void delete(String key);
        
        public void update(String key, String value);
        
        public List<String> getAll();
}