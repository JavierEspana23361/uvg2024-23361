import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Student implements IUser{
    UniSystem stm = new UniSystem();
    Scanner sc = new Scanner(System.in);
    Map<String, Object> Grades = new HashMap<>();
    Map<String, Object> Payments = new HashMap<>();


    public void showOptions(String username){

        stm.csvReader("Grades.csv", Grades);
        stm.csvReader("Payments.csv", Payments);
        System.out.println("1. Ver calificaciones");
        System.out.println("2. Pagar curso");
        System.out.println("3. Ver pagos");
        int option = sc.nextInt();

        if (option == 1) {
            System.out.println("Ingrese el nombre del curso:");
            String courseName = sc.next();
            checkGrades(courseName, username, Grades);

        } else if (option == 2) {
            System.out.println("Ingrese el nombre del curso:");
            String courseName = sc.next();
            doPayment("Payments.csv", courseName, Payments);
            
        } else if (option == 3) {
            checkPayments("Payments.csv", Payments);

        } else {
            System.out.println("Opción no válida.");
        }
    }
    
    //Se imprime la nota dado el curso dado el estudiante.

    public void checkGrades(String courseName, String studentName, Map<String, Object> dataMap) {
        String key = studentName + "," + courseName;
        String grade = (String) dataMap.get(key);
        if (grade != null) {
            System.out.println("Grade for " + studentName + " in " + courseName + ": " + grade);
        } else {
            System.out.println("No grade found for " + studentName + " in " + courseName);
        }
    }
    
    //Se realiza el pago de un curso dado el estudiante.

    public void doPayment(String csvFile, String courseName, Map<String, Object> dataMap) {
        System.out.println("Payments for " + courseName + ":");
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String studentName = entry.getKey();
            String payment = (String) entry.getValue(); 
            String[] keyParts = studentName.split(",");
            String course = keyParts[1];
            if (course.equals(courseName)) {
                System.out.println("Student: " + studentName + ", Payment: " + payment);
                dataMap.put(studentName, "Paid");
            }
        }
        // Reescribir el archivo CSV con el datamap actualizado
        stm.reWritecsv(csvFile, dataMap);
    }
    
    //Se imprimen los pagos de un curso dado el estudiante.

    public void checkPayments(String csvFile, Map<String, Object> dataMap) {
        System.out.println("Payments:");
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String studentName = entry.getKey();
            String payment = (String) entry.getValue(); 
            System.out.println("Student: " + studentName + ", Payment: " + payment);
        }
    }
}
