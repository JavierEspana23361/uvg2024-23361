import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Scanner;


public class Students {

    public int getMapType() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecciona el tipo de mapa que deseas utilizar: ");
        System.out.println("1. HashMap");
        System.out.println("2. TreeMap");
        System.out.println("3. LinkedHashMap");
        int option = sc.nextInt();
        sc.close();
        return option;
    }

   

    public Object studentstoMap(int option) {
        fileReader fr = new fileReader();
        switch (option) {
            case 1:
                HashMap<String, String> hashmap = new HashMap<>();
                List<String> estudiantesHashMap = fr.buscarEstudiantesPorNacionalidad("estudiantes.json", "nacionalidad");
                for (String estudiante : estudiantesHashMap) {
                    String[] parts = estudiante.split(":");
                    hashmap.put(parts[0].trim(), parts[1].trim());
                }
                return hashmap;

            case 2:
                TreeMap<String, String> treemap = new TreeMap<>();
                List<String> estudiantesTreeMap = fr.buscarEstudiantesPorNacionalidad("estudiantes.json", "nacionalidad");
                for (String estudiante : estudiantesTreeMap) {
                    String[] parts = estudiante.split(":");
                    treemap.put(parts[0].trim(), parts[1].trim());
                }
                return treemap;

            case 3:
                LinkedHashMap<String, String> linkedmap = new LinkedHashMap<>();
                List<String> estudiantesLinkedMap = fr.buscarEstudiantesPorNacionalidad("estudiantes.json", "nacionalidad");
                for (String estudiante : estudiantesLinkedMap) {
                    String[] parts = estudiante.split(":");
                    linkedmap.put(parts[0].trim(), parts[1].trim());
                }
                return linkedmap;

            default:
                return null;
        }
    }
}
