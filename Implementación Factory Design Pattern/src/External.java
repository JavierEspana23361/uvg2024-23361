import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class External implements IUser{
    UniSystem stm = new UniSystem();
    Scanner sc = new Scanner(System.in);
    Map<String, Object> Grades = new HashMap<>();
    Map<String, Object> Payments = new HashMap<>();
    Map<String, Object> TeacherPayments = new HashMap<>();

    public void showOptions(String username){
        stm.csvReader("Grades", Grades);
        stm.csvReader("Payments.csv", Payments);
        stm.csvReader("TPayment.csv", TeacherPayments);
        System.out.println("1. Ver calificaciones");
        System.out.println("2. Ver pagos de estudiantes");
        System.out.println("3. Ver pagos de profesores");
        int option = sc.nextInt();

        if (option == 1) {
            viewGrades(Grades);
        } else if (option == 2) {
            viewStudentPayments(Payments);
        } else if (option == 3) {
            viewTeacherPayments(TeacherPayments);
        } else {
            System.out.println("Opción no válida.");
        }
    }

    public void viewGrades(Map<String, Object> dataMap) {
        System.out.println("Grades:");
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String studentName = entry.getKey();
            String grade = (String) entry.getValue(); 
            System.out.println("Estudiante: " + studentName + ", Nota: " + grade);
        }
    }
    
    public void viewStudentPayments(Map<String, Object> dataMap){
        System.out.println("Payments:");
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String studentName = entry.getKey();
            String payment = (String) entry.getValue(); 
            System.out.println("Estudiante: " + studentName + ", Pago: " + payment);
        }
    }

    public void viewTeacherPayments(Map<String, Object> dataMap){
        System.out.println("Payments:");
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String teacherName = entry.getKey();
            String payment = (String) entry.getValue();
            System.out.println("Catedrático: " + teacherName + ", Cobro: " + payment);
        }
    }
}
