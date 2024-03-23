import java.util.Scanner;
import java.util.AbstractMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printMenuMap();
        int selectionMap = Integer.parseInt(scanner.nextLine());
        if (selectionMap > 3 || selectionMap < 1) {
            System.out.println("Error en la selección del tipo de mapa.");
           
        }

        printMenuHash();
        int selectionHash = Integer.parseInt(scanner.nextLine());
        if (selectionHash > 3 || selectionHash < 1) {
            System.out.println("Error en la selección del tipo de hash.");
      
        }

        MapFactory<String, Students> factoryMaps = new MapFactory<>();
        hashFactory factoryHash = new hashFactory();

        fileReader lectorArchivo = new fileReader(factoryMaps);

        AbstractMap<String, Students> map, mapWithStudents;
        Ihash hashMethod;

        map = factoryMaps.getMapInstance(selectionMap);
        hashMethod = factoryHash.gethash(selectionHash);

        mapWithStudents = lectorArchivo.readJsonFile("./estudiantes.json", map, hashMethod);

        boolean seguir = true;

        while (seguir) {
            System.out.println("Seleccione una opción: \n1. Buscar estudiantes por nombre\n2. Buscar estudiantes por nacionalidad\n3. Salir");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    Students Students = searchStudentbyKey(scanner, hashMethod, mapWithStudents);
                    if (Students != null) {
                        System.out.println(Students);
                    } else {
                        System.out.println("Estudiante no encontrado.");
                    }
                    break;
                case 2:
                    searchStudentbyNati(scanner, mapWithStudents);
                    break;
                case 3:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    public static void printMenuMap() {
        System.out.println("Ingrese el tipo de mapa con el que deseea guardar los Studentss:");
        System.out.println("1. HashMap");
        System.out.println("2. TreeMap");
        System.out.println("3. LinkedHashMap");
    }

    public static void printMenuHash() {
        System.out.println("Ingrese el tipo de hash que desea hacer:");
        System.out.println("1. Orgánica");
        System.out.println("2. MD5");
        System.out.println("3. SHA-1");
    }

    public static Students searchStudentbyKey(Scanner scanner, Ihash hashMethod, AbstractMap<String, Students> map) {
        String nameStudent;

        System.out.println("Ingrese el nombre del Students a buscar:");
        nameStudent = scanner.nextLine();

        String hashName = hashMethod.typehash(nameStudent);

        Students searchStudent = map.get(hashName);
        if (searchStudent != null) {
            return searchStudent;
        } else {
            return null;
        }
    }

    public static void searchStudentbyNati(Scanner scanner, AbstractMap<String, Students> map) {
        System.out.println("Ingrese la nacionalidad de los Studentss a buscar:");
        String nationality = scanner.nextLine();
        List<String> studentNames = new ArrayList<>();

        for (Map.Entry<String, Students> entry : map.entrySet()) {
            Students Students = entry.getValue();
            if (Students.getCountry().equalsIgnoreCase(nationality)) {
                studentNames.add(Students.getName());
            }
        }

        if (studentNames.isEmpty()) {
            System.out.println("No se encontraron Studentss de la nacionalidad " + nationality);
            return;
        }

        AbstractMap<String, List<Students>> studentsByNationality = saveByNationality(map, studentNames);

        studentsByNationality.forEach((key, value) -> {
            System.out.println("Nacionalidad: " + key);
            value.forEach(student -> System.out.println("- " + student.getName()));
        });
    }

    public static AbstractMap<String, List<Students>> saveByNationality(AbstractMap<String, Students> map, List<String> studentNames) {
        AbstractMap<String, List<Students>> mapByNationality = new HashMap<>();

        for (String name : studentNames) {
            for (Map.Entry<String, Students> entry : map.entrySet()) {
                Students Students = entry.getValue();
                if (Students.getName().equals(name)) {
                    mapByNationality.computeIfAbsent(Students.getCountry(), k -> new ArrayList<>()).add(Students);
                    break;
                }
            }
        }

        return mapByNationality;
    }
}