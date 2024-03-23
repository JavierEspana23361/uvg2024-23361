import java.util.Scanner;
import java.util.AbstractMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class App {
    /**
     * The main method is the entry point of the program.
     * It prompts the user to select a type of map and a type of hash,
     * reads a JSON file containing student data, and provides a menu
     * for searching students by name or nationality.
     *
     * @param args The command line arguments passed to the program.
     */
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

    /**
     * Prints the menu for selecting the type of map to store the Students.
     * The available options are HashMap, TreeMap, and LinkedHashMap.
     */
    public static void printMenuMap() {
        System.out.println("Ingrese el tipo de mapa con el que deseea guardar los Studentss:");
        System.out.println("1. HashMap");
        System.out.println("2. TreeMap");
        System.out.println("3. LinkedHashMap");
    }

    /**
     * Prints the menu options for selecting a hash type.
     * The available hash types are:
     * 1. Orgánica
     * 2. MD5
     * 3. SHA-1
     */
    public static void printMenuHash() {
        System.out.println("Ingrese el tipo de hash que desea hacer:");
        System.out.println("1. Orgánica");
        System.out.println("2. MD5");
        System.out.println("3. SHA-1");
    }

    /**
     * Searches for a student in the map using a given key.
     * 
     * @param scanner    the scanner object used to read user input
     * @param hashMethod the hash method used to generate the key
     * @param map        the map containing the students
     * @return the student object if found, null otherwise
     */
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

    /**
     * Searches for students by nationality in the given map and prints the results.
     *
     * @param scanner The scanner object used to read user input.
     * @param map The map containing the students.
     */
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

    /**
     * Saves the students from the given map by their nationality.
     * 
     * @param map The map containing the students.
     * @param studentNames The list of student names to save.
     * @return A map where the keys are the nationalities and the values are lists of students belonging to that nationality.
     */
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