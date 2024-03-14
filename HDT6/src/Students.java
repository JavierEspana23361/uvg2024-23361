import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Students {
    private Map<String, String> studentsMap;

    public void mapType(){
        Scanner sc = new Scanner(System.in);
        fileReader fr = new fileReader();
        System.out.println("Selecciona el tipo de mapa que deseas utilizar: ");
        System.out.println("1. HashMap");
        System.out.println("2. TreeMap");
        System.out.println("3. LinkedHashMap");
        int option = sc.nextInt();
        sc.nextLine(); // Consumir el salto de línea después del número
        switch(option){
            case 1:
                studentsMap = fr.readtoHasMap("estudiantes.json");
                break;
            case 2:
                studentsMap = fr.readtoTreeMap("estudiantes.json");
                break;
            case 3:
                studentsMap = fr.readtoLinkedHashMap("estudiantes.json");
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
        sc.close();
    }

    public Map<String, String> getStudentsMap() {
        return studentsMap;
    }
}
