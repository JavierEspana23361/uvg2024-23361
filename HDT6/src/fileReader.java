import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class fileReader {
    public HashMap<String, String> readToHashMap(String rutaArchivo) {
        try (FileReader fileReader = new FileReader(rutaArchivo)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(fileReader, new TypeReference<HashMap<String, String>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public TreeMap<String, String> readToTreeMap(String rutaArchivo) {
        try (FileReader fileReader = new FileReader(rutaArchivo)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(fileReader, new TypeReference<TreeMap<String, String>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LinkedHashMap<String, String> readToLinkedHashMap(String rutaArchivo) {
        try (FileReader fileReader = new FileReader(rutaArchivo)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(fileReader, new TypeReference<LinkedHashMap<String, String>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

