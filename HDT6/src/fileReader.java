import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class fileReader {
    public Map<String, String> readToMap(String rutaArchivo, String mapType) {
        try (FileReader fileReader = new FileReader(rutaArchivo)) {
            ObjectMapper objectMapper = new ObjectMapper();
            if (mapType.equalsIgnoreCase("linked")) {
                return objectMapper.readValue(fileReader, new TypeReference<LinkedHashMap<String, String>>() {});
            } else if (mapType.equalsIgnoreCase("hash")) {
                return objectMapper.readValue(fileReader, new TypeReference<HashMap<String, String>>() {});
            } else if (mapType.equalsIgnoreCase("tree")) {
                return objectMapper.readValue(fileReader, new TypeReference<TreeMap<String, String>>() {});
            } else {
                throw new IllegalArgumentException("Tipo de mapa no válido: " + mapType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> buscarEstudiantesPorNacionalidad(String rutaArchivo, String nacionalidad, Map<String, String> map) {
        List<String> estudiantesEncontrados = new ArrayList<>();
        try (FileReader fileReader = new FileReader(rutaArchivo)) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, String>> estudiantes = objectMapper.readValue(fileReader, new TypeReference<List<Map<String, String>>>() {});
            for (Map<String, String> estudiante : estudiantes) {
                if (estudiante.containsKey("nacionalidad") && estudiante.get("nacionalidad").equalsIgnoreCase(nacionalidad)) {
                    estudiantesEncontrados.add(estudiante.get("nombre"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estudiantesEncontrados;
    }

    public String buscarEstudiantePorClave(String rutaArchivo, String clave, Map<String, String> map) {
        try (FileReader fileReader = new FileReader(rutaArchivo)) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, String>> estudiantes = objectMapper.readValue(fileReader, new TypeReference<List<Map<String, String>>>() {});
            for (Map<String, String> estudiante : estudiantes) {
                if (estudiante.containsKey(clave)) {
                    return estudiante.get(clave);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
