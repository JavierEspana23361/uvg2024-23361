import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.io.FileWriter;
import java.util.Scanner;

public class UniSystem {
    Scanner sc = new Scanner(System.in);

    public void csvReader(String csvFile, Map<String, Object> dataMap) {
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

    public void reWritecsv(String csvFile, Map<String, Object> dataMap) {
        try {
            FileWriter writer = new FileWriter(csvFile);
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                writer.append(entry.getKey());
                writer.append(",");
                writer.append(String.valueOf(entry.getValue())); 
                writer.append("\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String login(Map<String, Object> dataMap) {
        System.out.println("Ingrese el nombre de usuario:");
        String username = sc.next();
        if (dataMap.containsKey(username)) {
            return username;
        } else {
            System.out.println("El usuario no existe en el sistema.");
            return null;
        }
    }

    public String evalType(String username, Map<String, Object> dataMap) {
        if (dataMap.containsKey(username)) {
            return (String) dataMap.get(username);
        } else {
            return null;
        }
    }

    public void register(Map<String, Object> dataMap) {
        System.out.println("Ingrese el nombre de usuario: ");
        String username = sc.next();
        System.out.println("Ingrese el tipo de usuario (student/teacher/admin/external): ");
        String usertype = sc.next();
        if (dataMap.containsKey(username)) {
            System.out.println("El usuario ya existe en el sistema.");
            return;
        } else {
            if (usertype.equals("student") || usertype.equals("teacher") || usertype.equals("admin") || usertype.equals("external")) {
                dataMap.put(username, usertype);
                reWritecsv("Users.csv", dataMap);
            } else {
                System.out.println("Tipo de usuario no válido.");
                return;
            }
            dataMap.put(username, usertype);
            reWritecsv("Users.csv", dataMap);
        }
    }
}
    

