import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.TreeMap;

public class fileReader {
    public HashMap<String, String> readtoHasMap(String rutaArchivo) {
        HashMap<String, String> map = new HashMap<>();
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

    public TreeMap<String, String> readtoTreeMap(String rutaArchivo) {
        TreeMap<String, String> map = new TreeMap<>();
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

    public LinkedHashMap<String, String> readtoLinkedHashMap(String rutaArchivo) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(br, new TypeReference<LinkedHashMap<String, String>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public String buscarEstudiantePorClave(String rutaArchivo, String clave) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].trim().equals(clave)) {
                    return parts[1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<String> buscarEstudiantesPorNacionalidad(String rutaArchivo, String nacionalidad) {
    List<String> estudiantesEncontrados = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 3 && parts[1].trim().equalsIgnoreCase(nacionalidad)) {
                estudiantesEncontrados.add(parts[0].trim()); // Agregar el nombre del estudiante encontrado
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return estudiantesEncontrados;
    }
}
