import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.io.FileWriter;



public class UniSystem {
    public void csvReader(String csvFile, Map<String, String> dataMap) {
        String line = "";
        String csvSplitBy = ",";


        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length >= 2) {
                    dataMap.put(data[0], data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    

    public void reWritecsv(String csvFile, Map<String, String> dataMap) {
        try {
            FileWriter writer = new FileWriter(csvFile);
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                writer.append(entry.getKey());
                writer.append(",");
                writer.append(entry.getValue());
                writer.append("\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
   
    public void addGradesforaStudentinCourse (String csvFile, String studentName, String courseName, String grade, Map<String, String> dataMap) {
        String key = studentName + "," + courseName;
        dataMap.put(key, grade);
        reWritecsv(csvFile, dataMap);
    }
    
}
    

