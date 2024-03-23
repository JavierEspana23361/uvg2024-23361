import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.AbstractMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class fileReader {
    private Map<String, Object> map = new LinkedHashMap<>();
    public static AbstractMap<String, Object> readJsonFile(String filePath) {
        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Read JSON file and convert it to a Map
            map = objectMapper.readValue(new FileReader(filePath), new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}

