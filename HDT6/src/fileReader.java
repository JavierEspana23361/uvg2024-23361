import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class fileReader {
    public List<Student> leerEstudiantesDesdeJSON(String rutaArchivo) {
        List<Student> estudiantes = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(rutaArchivo)) {
            Object obj = parser.parse(reader);
            JSONArray listaEstudiantes = (JSONArray) obj;

            for (Object estudianteObj : listaEstudiantes) {
                JSONObject estudianteJSON = (JSONObject) estudianteObj;
                String nombre = (String) estudianteJSON.get("nombre");
                String nacionalidad = (String) estudianteJSON.get("nacionalidad");
                Student estudiante = new Student(nombre, nacionalidad);
                estudiantes.add(estudiante);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return estudiantes;
    }
}
