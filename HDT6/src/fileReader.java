import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class fileReader {
    private MapFactory<String, Students> mapFactory;
    private Ihash hashtype;

    public fileReader(MapFactory<String, Students> mapFactory) {
        this.mapFactory = mapFactory;
    }
    
    public AbstractMap<String, Students> readJsonFile(String filePath, AbstractMap<String, Students> studentsmap) {
        JSONParser parser = new JSONParser();

        try (FileReader fileReader = new FileReader(filePath)) {
            Object obj = parser.parse(fileReader);
            JSONArray jsonArray = (JSONArray) obj;

            for (Object studentObj : jsonArray) {
                JSONObject studentJson = (JSONObject) studentObj;
                parseStudentObject(studentJson, studentsmap);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return studentsmap;
    }

    private void parseStudentObject(JSONObject studentJson, AbstractMap<String, Students> studentsmap) {
        String name = (String) studentJson.get("name");
        String phone = (String) studentJson.get("phone");
        String email = (String) studentJson.get("email");
        String postalZip = (String) studentJson.get("postalZip");
        String country = (String) studentJson.get("country");

        Students student = new Students(name, phone, email, postalZip, country);
        String key = hashtype.typehash(name);
        studentsmap.put(key, student);
    }

}

