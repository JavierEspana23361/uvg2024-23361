import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import com.fasterxml.jackson.databind.ObjectMapper;



public class Admin implements IUser{
    UniSystem stm = new UniSystem();
    Scanner sc = new Scanner(System.in);
    Map<String, Object> Students = new HashMap<>();
    Map<String, Object> Teachers = new HashMap<>();
    Map<String, Object> Courses = new HashMap<>();
    Map<String, Object> Grades = new HashMap<>();
    Map<String, Object> Payments = new HashMap<>();
    Map<String, Object> TPayment = new HashMap<>();
    Map<String, Object> Users = new HashMap<>();

    public void showOptions(String username){
        stm.csvReader("Courses.csv", Courses);
        stm.csvReader("Students.csv", Students);
        stm.csvReader("Teachers.csv", Teachers);
        stm.csvReader("Grades.csv", Grades);
        stm.csvReader("Payments.csv", Payments);
        stm.csvReader("TPayment.csv", TPayment);
        stm.csvReader("Users.csv", Users);
        int option = 0;
        while (option != 9) {
            System.out.println("Opciones:");
            System.out.println("1. Añadir curso");
            System.out.println("2. Añadir estudiante");
            System.out.println("3. Añadir profesor");
            System.out.println("4. Añadir estudiante a curso");
            System.out.println("5. Añadir profesor a curso");
            System.out.println("6. Asignar pago a profesor");
            System.out.println("7. Resumen de calificaciones");
            System.out.println("8. Resumen de pagos");
            System.out.println("9. Salir");

            option = sc.nextInt();
            if (option == 1){
                System.out.println("Ingrese el nombre del curso:");
                String courseName = sc.next();
                addCourse("Courses.csv", courseName, Courses);

            } else if (option == 2){
                System.out.println("Ingrese el nombre del estudiante:");
                String studentName = sc.next();
                addStudent("Students.csv", studentName, Students, "Users.csv", Users);

            } else if (option == 3){
                System.out.println("Ingrese el nombre del profesor:");
                String teacherName = sc.next();
                addTeacher("Teachers.csv", teacherName, Teachers, "Users.csv", Users);

            } else if (option == 4){
                System.out.println("Ingrese el nombre del estudiante:");
                String studentName = sc.next();
                System.out.println("Ingrese el nombre del curso:");
                String courseName = sc.next();
                addStudenttoCourse("Students.csv", studentName, courseName, Students, "Courses.csv", Courses);

            } else if (option == 5){
                System.out.println("Ingrese el nombre del profesor:");
                String teacherName = sc.next();
                System.out.println("Ingrese el nombre del curso:");
                String courseName = sc.next();
                addTeachertoCourse("Teachers.csv", teacherName, courseName, Teachers, "Courses.csv", Courses);

            } else if (option == 6){
                assignPayToTeacher("TPayment.csv", Payments, TPayment, TPayment);

            } else if (option == 7){
                System.out.println("1. Generar resumen de calificaciones en CSV");
                System.out.println("2. Generar resumen de calificaciones en JSON");
                int option2 = sc.nextInt();
                if (option2 == 1){
                    gradesSummary(Grades);
                } else if (option2 == 2){
                    generateGradesSummaryJson(Grades);
                } else {
                    System.out.println("Opción no válida.");
                }
            } else if (option == 8){
                paymentSummary(Payments);

            } else {
            System.out.println("Opción no válida.");
        }
        }
    }

    public void addStudent(String csvFile, String studentName, Map<String, Object> dataMap, String csvFile2, Map<String, Object> dataMap2) {
        if (dataMap.containsKey(studentName)) {
            System.out.println("El estudiante ya existe en el sistema.");
            return;
        } else {
            dataMap.put(studentName, "");
            stm.reWritecsv(csvFile, dataMap);
            if (dataMap2.containsKey(studentName)) {
                System.out.println("El estudiante ya existe en el sistema.");
                return;
            } else {
                dataMap2.put(studentName, "student");
                stm.reWritecsv(csvFile2, dataMap2);
            }
        }
    }

    public void addTeacher (String csvFile, String teacherName, Map<String, Object> dataMap, String csvFile2, Map<String, Object> dataMap2){
        if (dataMap.containsKey(teacherName)) {
            System.out.println("El profesor ya existe en el sistema.");
            return;
        } else {
            dataMap.put(teacherName, "");
            stm.reWritecsv(csvFile, dataMap);
            if (dataMap2.containsKey(teacherName)) {
                System.out.println("El estudiante ya existe en el sistema.");
                return;
            } else {
                dataMap2.put(teacherName, "student");
                stm.reWritecsv(csvFile2, dataMap2);
            }
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
    
    public void addStudenttoCourse(String csvFile, String studentName, String courseName, Map<String, Object> dataMap, String csvFile2, Map<String, Object> dataMap2) {
        if (dataMap2.containsKey(courseName)){
            if (dataMap.containsKey(studentName)) {
                String course = (String) dataMap.get(studentName);
                course = course + "," + courseName;
                dataMap.put(studentName, course);
            } else {
            dataMap.put(studentName, courseName);
            stm.reWritecsv(csvFile, dataMap);
            }
        } else {
            System.out.println("El curso no existe en el sistema.");
            return;
        }
    }

    public void addTeachertoCourse(String csvFile, String teacherName, String courseName, Map<String, Object> dataMap, String csvFile2, Map<String, Object> dataMap2) {
        if (dataMap2.containsKey(courseName)){
            if (dataMap.containsKey(teacherName)) {
                String course = (String) dataMap.get(teacherName);
                course = course + "," + courseName;
                dataMap.put(teacherName, course);
            } else {
            dataMap.put(teacherName, courseName);
            stm.reWritecsv(csvFile, dataMap);
            }
        }  else {
            System.out.println("El curso no existe en el sistema.");
            return;
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

        try (FileWriter writer = new FileWriter("grades_summary.csv")) {
            writer.append("Student,Course,Grade\n");

            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                String[] keyParts = entry.getKey().split(",");
                String studentName = keyParts[0];
                String courseName = keyParts[1];
                String grade = (String) entry.getValue(); // Cast the value to String

                writer.append(studentName)
                        .append(",")
                        .append(courseName)
                        .append(",")
                        .append(grade)
                        .append("\n");
            }

            System.out.println("Grades Summary CSV file generated successfully.");
        } catch (IOException e) {
            System.out.println("Failed to generate Grades Summary CSV file.");
            e.printStackTrace();
        }
    }
    
    public void generateGradesSummaryJson(Map<String, Object> dataMap) {
        System.out.println("Generating Grades Summary JSON...");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("grades_summary.json"), dataMap);
            System.out.println("Grades Summary JSON file generated successfully.");
        } catch (IOException e) {
            System.out.println("Failed to generate Grades Summary JSON file.");
            e.printStackTrace();
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

        try (FileWriter writer = new FileWriter("payment_summary.csv")) {
            writer.append("Teacher,Total Payment\n");

            for (Map.Entry<String, Double> entry : teacherPayments.entrySet()) {
                String teacherName = entry.getKey();
                double totalPayment = entry.getValue();

                writer.append(teacherName)
                        .append(",")
                        .append(String.valueOf(totalPayment))
                        .append("\n");
            }

            System.out.println("Payment Summary CSV file generated successfully.");
        } catch (IOException e) {
            System.out.println("Failed to generate Payment Summary CSV file.");
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("payment_summary.json"), teacherPayments);
            System.out.println("Payment Summary JSON file generated successfully.");
        } catch (IOException e) {
            System.out.println("Failed to generate Payment Summary JSON file.");
            e.printStackTrace();
        }
    }

}

