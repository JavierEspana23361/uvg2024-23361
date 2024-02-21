import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
public class Teacher implements IUser{
    UniSystem stm = new UniSystem();
    Scanner sc = new Scanner(System.in);
    Map<String, Object> Courses = new HashMap<>();
    Map<String, Object> TPayment = new HashMap<>();

    public void showOptions(String username){
        stm.csvReader("TPayment.csv", TPayment);
        stm.csvReader("Courses.csv", Courses);

        int option = 0;
        while (option != 4){
            System.out.println("1. Ver calificaciones");
            System.out.println("2. Ver pagos");
            System.out.println("3. Cobrar");
            System.out.println("4. Salir");
            option = sc.nextInt();
            
            String courseName = getCourse(username, Courses);
            if(option == 1){
                System.out.println("Ingrese el nombre del estudiante:");
                String studentName = sc.next();
                addStudenGrade("Grades.csv", studentName, courseName, username, null);

            } else if(option == 2){
                getPaymentAmount(TPayment, username);
            } else if(option == 3){
                getPayment("TPayment.csv", username, TPayment);
            } else {
                System.out.println("Opción no válida.");
            }
    }
    }
    
    public String getCourse(String username, Map<String, Object> dataMap){
        if (dataMap.containsKey(username)) {
            return (String) dataMap.get(username);
        } else {
            return null;
        }
    }
    
    public void addStudenGrade (String csvFile, String studentName, String courseName, String grade, Map<String, Object> dataMap) {
        String key = studentName + "," + courseName;
        dataMap.put(key, grade);
        stm.reWritecsv(csvFile, dataMap);
    }

    public void getPayment(String csvString, String teacherName, Map<String, Object> dataMap){
        dataMap.put(teacherName, "");
        stm.reWritecsv(csvString, dataMap);
        System.out.println("Cobro realizado con éxito. El dinero fue depositado en la cuenta del profesor, por ende, la cantidad disponible es 0");
        
    }

    public double getPaymentAmount(Map<String, Object> dataMap, String teacherName) {
        if (dataMap.containsKey(teacherName)) {
            Object paymentAmount = dataMap.get(teacherName);
            if (paymentAmount instanceof Double) {
                return (double) paymentAmount;
            }
        }
        return 0.0; 
    }
}


