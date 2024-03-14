import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.util.LinkedHashMap;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.TreeMap;

public class fileReader {
    public Map<String, String> readtoHasMap(String rutaArchivo) {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    map.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, String> readtoTreeMap(String rutaArchivo) {
        Map<String, String> map = new TreeMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    map.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, String> readtoLinkedHashMap(String rutaArchivo) {
        Map<String, String> map = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(br, new TypeReference<LinkedHashMap<String, String>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
