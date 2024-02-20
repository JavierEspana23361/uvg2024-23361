import java.util.Map;

public class Student implements IUser{
    UniSystem stm = new UniSystem();
    public void showOptions(){
        
    }
    //Se imprime la nota dado el curso dado el estudiante.
    public void checkGrades(String csvFile, String courseName, String studentName, Map<String, String> dataMap) {
        String key = studentName + "," + courseName;
        String grade = dataMap.get(key);
        if (grade != null) {
            System.out.println("Grade for " + studentName + " in " + courseName + ": " + grade);
        } else {
            System.out.println("No grade found for " + studentName + " in " + courseName);
        }
    }
    //Se realiza el pago de un curso dado el estudiante.
    public void doPayment(String csvFile, String courseName, Map<String, String> dataMap) {
        System.out.println("Payments for " + courseName + ":");
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String studentName = entry.getKey();
            String payment = entry.getValue();
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

    public void checkPayments (String csvFile, Map<String, String> dataMap) {
        System.out.println("Payments:");
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String studentName = entry.getKey();
            String payment = entry.getValue();
            System.out.println("Student: " + studentName + ", Payment: " + payment);
        }
    }
}
