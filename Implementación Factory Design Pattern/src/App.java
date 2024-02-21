import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        UniSystem stm = new UniSystem();
        Map<String, Object> Users = new HashMap<>();
        stm.csvReader("Users.csv", Users);
        Scanner scanner = new Scanner(System.in);
        String username = null;

        while (username == null) {
            System.out.println("Opciones: 1. Login 2. Register 3. Salir");
            int option = scanner.nextInt();
            
            if (option == 1) {
                username = stm.login(Users);
            } else if (option == 2) {
                stm.register(Users);
            } else {
                System.exit(1);
            }
        
        }
        String Usertype = stm.evalType(username, Users);

        UserFactory factory = null;
        switch (Usertype.toLowerCase()) {
            case "student":
                factory = new StudentFactory();
                break;
            case "teacher":
                factory = new TeacherFactory();
                break;
            case "admin":
                factory = new AdminFactory();
                break;
            case "external":
                factory = new ExternalFactory();
                break;
            default:
                System.out.println("Tipo de usuario no válido.");
                System.exit(1);
        }
        IUser user = factory.createUser();
        user.showOptions(username);
        
        scanner.close();
    }
}
