import java.util.HashMap;
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
                HashMap<String, String> map = new HashMap<>();
                map = fr.readtoHasMap("estudiantes.json");
                return null;
            case 2:
                fr.readtoTreeMap("estudiantes.json");
                return null;
            case 3:
                fr.readtoLinkedHashMap("estudiantes.json");
                return null;
            default:
                System.out.println("Opción no válida");
                return null;
                
        }
        
    }
}
