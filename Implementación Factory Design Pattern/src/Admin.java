import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Admin implements IUser{
    UniSystem stm = new UniSystem();
    Scanner sc = new Scanner(System.in);
    Map<String, Object> Students = new HashMap<>();
    Map<String, Object> Teachers = new HashMap<>();
    Map<String, Object> Courses = new HashMap<>();
    Map<String, Object> Grades = new HashMap<>();
    Map<String, Object> Payments = new HashMap<>();
    Map<String, Object> TPayment = new HashMap<>();

    public void showOptions(String username){
        stm.csvReader("Courses.csv", Courses);
        stm.csvReader("Students.csv", Students);
        stm.csvReader("Teachers.csv", Teachers);
        stm.csvReader("Grades.csv", Grades);
        stm.csvReader("Payments.csv", Payments);
        stm.csvReader("TPayment.csv", TPayment);

        System.out.println("1. Añadir curso");
        System.out.println("2. Añadir estudiante");
        System.out.println("3. Añadir profesor");
        System.out.println("4. Añadir estudiante a curso");
        System.out.println("5. Añadir profesor a curso");
        System.out.println("6. Asignar pago a profesor");
        System.out.println("7. Resumen de calificaciones");
        System.out.println("8. Resumen de pagos");

        int option = sc.nextInt();
        if (option == 1){
            System.out.println("Ingrese el nombre del curso:");
            String courseName = sc.next();
            addCourse("Courses.csv", courseName, Courses);

        } else if (option == 2){
            System.out.println("Ingrese el nombre del estudiante:");
            String studentName = sc.next();
            System.out.println("Ingrese el ID del estudiante:");
            String studentID = sc.next();
            addStudent("Students.csv", studentName, studentID, Students);

        } else if (option == 3){
            System.out.println("Ingrese el nombre del profesor:");
            String teacherName = sc.next();
            System.out.println("Ingrese el ID del profesor:");
            String teacherID = sc.next();
            addTeacher("Teachers.csv", teacherName, teacherID, Teachers);

        } else if (option == 4){
            System.out.println("Ingrese el nombre del estudiante:");
            String studentName = sc.next();
            System.out.println("Ingrese el nombre del curso:");
            String courseName = sc.next();
            addStudenttoCourse("Grades.csv", studentName, courseName, Grades);

        } else if (option == 5){
            System.out.println("Ingrese el nombre del profesor:");
            String teacherName = sc.next();
            System.out.println("Ingrese el nombre del curso:");
            String courseName = sc.next();
            addTeachertoCourse("TPayment.csv", teacherName, courseName, TPayment);

        } else if (option == 6){
            assignPayToTeacher("TPayment.csv", Payments, TPayment, TPayment);

        } else if (option == 7){
            gradesSummary(Grades);

        } else if (option == 8){
            paymentSummary(Payments);

        } else {
            System.out.println("Opción no válida.");
        }
    }

    public void addStudent(String csvFile, String studentName, String studentID, Map<String, Object> dataMap) {
        if (dataMap.containsKey(studentName)) {
            System.out.println("El estudiante ya existe en el sistema.");
            return;
        } else {
            dataMap.put(studentName, studentID);
            stm.reWritecsv(csvFile, dataMap);
        }
    }

    public void addTeacher (String csvFile, String teacherName, String teacherID, Map<String, Object> dataMap) {
        if (dataMap.containsKey(teacherName)) {
            System.out.println("El profesor ya existe en el sistema.");
            return;
        } else {
            dataMap.put(teacherName, teacherID);
            stm.reWritecsv(csvFile, dataMap);
        }
    }

    public void addCourse(String csvFile, String courseName, Map<String, Object> dataMap) {
        if (dataMap.containsKey(courseName)) {
            System.out.println("El curso ya existe en el sistema.");
            return;
        } else {
            dataMap.put(courseName, "");
            stm.reWritecsv(csvFile, dataMap);
        }
    }
    
    public void addStudenttoCourse(String csvFile, String studentName, String courseName, Map<String, Object> dataMap) {
        if (dataMap.containsKey(studentName)) {
            String course = (String) dataMap.get(studentName);
            course = course + "," + courseName;
            dataMap.put(studentName, course);
        } else {
        dataMap.put(studentName, courseName);
        stm.reWritecsv(csvFile, dataMap);
        }
    }

    public void addTeachertoCourse(String csvFile, String teacherName, String courseName, Map<String, Object> dataMap) {
        if (dataMap.containsKey(teacherName)) {
            String course = (String) dataMap.get(teacherName);
            course = course + "," + courseName;
            dataMap.put(teacherName, course);
        } else {
        dataMap.put(teacherName, courseName);
        stm.reWritecsv(csvFile, dataMap);
        }
    }

    public void assignPayToTeacher(String csvFile, Map<String, Object> dataMap, Map<String, Object> teacherCourses, Map<String, Object> TPayment) {
        for (Map.Entry<String, Object> entry : Payments.entrySet()) {
            String studentName = entry.getKey();
            String payment = (String) entry.getValue();

            String[] keyParts = studentName.split(",");
            String teacherName = keyParts[0];
            String courseName = keyParts[1];
            double paymentAmount = Double.parseDouble(payment);

            if (teacherCourses.containsKey(teacherName) && teacherCourses.get(teacherName).equals(courseName)) {
                if (TPayment.containsKey(teacherName)) {
                    double currentPayment = (double) TPayment.get(teacherName); 
                    TPayment.put(teacherName, currentPayment + paymentAmount);
                } else {
                    TPayment.put(teacherName, paymentAmount);
                }
            }
        }

        for (Map.Entry<String, Object> entry : TPayment.entrySet()) {
            String teacherName = entry.getKey();
            double totalPayment = (double) entry.getValue();
            System.out.println("Teacher: " + teacherName + ", Total Payment: " + totalPayment);
        
        }
        stm.reWritecsv(csvFile, TPayment);
    }

    public void gradesSummary(Map<String, Object> dataMap) {
        System.out.println("Grades Summary Report:");
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String[] keyParts = entry.getKey().split(",");
            String studentName = keyParts[0];
            String courseName = keyParts[1];
            String grade = (String) entry.getValue(); // Cast the value to String
            System.out.println("Student: " + studentName + ", Course: " + courseName + ", Grade: " + grade);
        }
    }

    public void paymentSummary(Map<String, Object> dataMap) {
        System.out.println("Payments Report:");
        Map<String, Double> teacherPayments = new HashMap<>();

        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String studentName = entry.getKey();
            String payment = entry.getValue().toString();
            System.out.println("Student: " + studentName + ", Payment: " + payment);

            String[] keyParts = studentName.split(",");
            String teacherName = keyParts[0];
            double paymentAmount = Double.parseDouble(payment);

            if (teacherPayments.containsKey(teacherName)) {
                double currentPayment = teacherPayments.get(teacherName);
                teacherPayments.put(teacherName, currentPayment + paymentAmount);
            } else {
                teacherPayments.put(teacherName, paymentAmount);
            }
        }

        for (Map.Entry<String, Double> entry : teacherPayments.entrySet()) {
            String teacherName = entry.getKey();
            double totalPayment = entry.getValue();
            System.out.println("Teacher: " + teacherName + ", Total Payment: " + totalPayment);
        }
    }

}
