public class App {
    public static void main(String[] args) {
        Students students = new Students();
        int mapType = students.getMapType();
        String mapTypeString = null;
        switch (mapType) {
            case 1:
                mapTypeString = "hashmap";
                break;
            case 2:
                mapTypeString = "treemap";
                break;
            case 3:
                mapTypeString = "linkedhashmap";
                break;
            default:
                System.out.println("Tipo de mapa no válido.");
                System.exit(0);
        }

        //Map<String, String> estudiantesMap = students.studentstoMap(mapType, mapTypeString);
        // Aquí se puede proceder con el uso del mapa de estudiantes según el tipo seleccionado
    }
}