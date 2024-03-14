import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.Scanner;


public class Students {
    public Object mapType(){
        Scanner sc = new Scanner(System.in);
        fileReader fr = new fileReader();
        System.out.println("Selecciona el tipo de mapa que deseas utilizar: ");
        System.out.println("1. HashMap");
        System.out.println("2. TreeMap");
        System.out.println("3. LinkedHashMap");
        int option = sc.nextInt();
        sc.close();
        switch(option){
            case 1:
                HashMap<String, String> hashmap = new HashMap<>();
                hashmap = fr.readtoHasMap("estudiantes.json");
                return hashmap;
            case 2:
                TreeMap<String, String> treemap = new TreeMap<>();
                treemap = fr.readtoTreeMap("estudiantes.json");
                return treemap;
            case 3:
                LinkedHashMap<String, String> linkedmap = new LinkedHashMap<>();
                linkedmap = fr.readtoLinkedHashMap("estudiantes.json");
                return linkedmap;
            default:
                System.out.println("Opción no válida");
                return null;
                
        }
        
    }
}
