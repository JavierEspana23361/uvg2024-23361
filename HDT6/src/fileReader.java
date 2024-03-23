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

    /**
     * Constructs a new fileReader object with the specified mapFactory.
     *
     * @param mapFactory the map factory to be used for creating the map
     */
    public fileReader(MapFactory<String, Students> mapFactory) {
        this.mapFactory = mapFactory;
    }
    
    /**
     * Reads a JSON file and populates a map of students.
     *
     * @param filePath     the path of the JSON file to be read
     * @param studentsmap  the map to store the students
     * @param hashtype     the hash type to be used for student IDs
     * @return the map of students populated with data from the JSON file
     */
    public AbstractMap<String, Students> readJsonFile(String filePath, AbstractMap<String, Students> studentsmap, Ihash hashtype) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            Object obj = parser.parse(reader);

            JSONArray student = (JSONArray) obj;

            student.forEach((Object studentJson) -> parseStudentObject((JSONObject) studentJson, studentsmap, hashtype));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return studentsmap;
    }

    /**
     * Parses a JSON object representing a student and adds it to the students map.
     * 
     * @param studentJson the JSON object representing the student
     * @param studentsmap the map to store the parsed student object
     * @param hashtype the hash type to use for generating the key
     */
    private void parseStudentObject(JSONObject studentJson, AbstractMap<String, Students> studentsmap, Ihash hashtype) {
        String name = (String) studentJson.get("name");
        String phone = (String) studentJson.get("phone");
        String email = (String) studentJson.get("email");
        String postalZip = (String) studentJson.get("postalZip");
        String country = (String) studentJson.get("country");
        String hash = hashtype.typehash(name);


        studentsmap.put(hash, new Students(name, phone, email, postalZip, country));
    }

}

