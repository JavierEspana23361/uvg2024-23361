import java.util.HashMap;
import java.util.Map;

public class Admin implements IUser{
    UniSystem stm = new UniSystem();
    
    public void showOptions(){
        System.out.println("1. Añadir curso");
        System.out.println("2. Añadir estudiante");
        System.out.println("3. Añadir profesor");
        System.out.println("4. Añadir estudiante a curso");
        System.out.println("5. Añadir profesor a curso");
        System.out.println("6. Asignar pago a profesor");
        System.out.println("7. Resumen de calificaciones");
        System.out.println("8. Resumen de pagos");
    }

    public void addStudent(String csvFile, String studentName, String studentID, Map<String, String> dataMap) {
        if (dataMap.containsKey(studentName)) {
            System.out.println("El estudiante ya existe en el sistema.");
            return;
        } else {
            dataMap.put(studentName, studentID);
            stm.reWritecsv(csvFile, dataMap);
        }
    }

    public void addTeacher (String csvFile, String teacherName, String teacherID, Map<String, String> dataMap) {
        if (dataMap.containsKey(teacherName)) {
            System.out.println("El profesor ya existe en el sistema.");
            return;
        } else {
            dataMap.put(teacherName, teacherID);
            stm.reWritecsv(csvFile, dataMap);
        }
    }

    public void addCourse(String csvFile, String courseName, Map<String, String> dataMap) {
        if (dataMap.containsKey(courseName)) {
            System.out.println("El curso ya existe en el sistema.");
            return;
        } else {
            dataMap.put(courseName, "");
            stm.reWritecsv(csvFile, dataMap);
        }
    }
    
    public void addStudenttoCourse(String csvFile, String studentName, String courseName, Map<String, String> dataMap) {
        if (dataMap.containsKey(studentName)) {
            String course = dataMap.get(studentName);
            course = course + "," + courseName;
            dataMap.put(studentName, course);
        } else {
        dataMap.put(studentName, courseName);
        stm.reWritecsv(csvFile, dataMap);
        }
    }

    public void addTeachertoCourse(String csvFile, String teacherName, String courseName, Map<String, String> dataMap) {
        if (dataMap.containsKey(teacherName)) {
            String course = dataMap.get(teacherName);
            course = course + "," + courseName;
            dataMap.put(teacherName, course);
        } else {
        dataMap.put(teacherName, courseName);
        stm.reWritecsv(csvFile, dataMap);
        }
    }

    public void assignpaytoTeacher(){
        
    }

    public void gradesSummary(Map<String, String> dataMap) {
        System.out.println("Grades Summary Report:");
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String[] keyParts = entry.getKey().split(",");
            String studentName = keyParts[0];
            String courseName = keyParts[1];
            String grade = entry.getValue();
            System.out.println("Student: " + studentName + ", Course: " + courseName + ", Grade: " + grade);
        }
    }
    public void paymentSummary(Map<String, String> dataMap) {
        System.out.println("Payments Report:");
        Map<String, Double> teacherPayments = new HashMap<>();

        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String studentName = entry.getKey();
            String payment = entry.getValue();
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
