import java.util.Map;
public class Teacher implements IUser{
    UniSystem stm = new UniSystem();

    public void showOptions(String username){
        
    }
    
    public void addStudenGrade (String csvFile, String studentName, String courseName, String grade, Map<String, Object> dataMap) {
        String key = studentName + "," + courseName;
        dataMap.put(key, grade);
        stm.reWritecsv(csvFile, dataMap);
    }



    public void paymentRecord(){
        
    }

}
