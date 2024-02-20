import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class USystem {
    Map<String, String> Students = new HashMap<>();
    Map<String, String> Teachers = new HashMap<>();
    Map<String, String> Courses = new HashMap<>();
    Map<String, String> Grades = new HashMap<>();
    Map<String, String> Payments = new HashMap<>();
    Map<String, String> TPayment = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su tipo de usuario (estudiante/docente/admin/auditor):");
        String Usertype = scanner.nextLine();

        UserFactory factory = null;
        switch (Usertype.toLowerCase()) {
            case "estudiante":
                factory = new StudentFactory();
                break;
            case "docente":
                factory = new TeacherFactory();
                break;
            case "admin":
                factory = new AdminFactory();
                break;
            case "auditor":
                factory = new ExternalFactory();
                break;
            default:
                System.out.println("Tipo de usuario no válido.");
                System.exit(1);
        }

        // Creación del usuario y presentación de opciones
        IUser user = factory.createUser();
        user.showOptions();
        scanner.close();
    }
}
