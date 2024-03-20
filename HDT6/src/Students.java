import java.util.Map;
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

    public Map<String, String> studentstoMap(int option, String mapType) {
        String filePath = "estudiantes.json"; // Ruta del archivo JSON
        fileReader fr = new fileReader();
        switch (option) {
            case 1:
                return fr.readToMap(filePath, mapType);
            case 2:
                return fr.readToMap(filePath, mapType);
            case 3:
                return fr.readToMap(filePath, mapType);
            default:
                return null;
        }
    }
}
