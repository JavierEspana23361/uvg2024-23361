import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class fileReader {
    public Arraylist<String> leerEstudiantesDesdeJSON(String rutaArchivo) {
        ArrayList<String> estudiantes = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try (FileReader reader = new FileReader(rutaArchivo)) {
            Student[] estudiantesArray = objectMapper.readValue(reader, Student[].class);
            estudiantes.addAll(Arrays.asList(estudiantesArray));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return estudiantes;
    }
}
