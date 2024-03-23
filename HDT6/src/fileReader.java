import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class fileReader {

    private MapFactory<String, Students> mapFactory;

    public fileReader(MapFactory<String, Students> mapFactory) {
        this.mapFactory = mapFactory;
    }

    public static AbstractMap<String, Studen> readJsonFile(String filePath, AbstractMap<String, Students> studentsmap), Ihash hashtype{
        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Read JSON file and convert it to a Map
            map = objectMapper.readValue(new FileReader(filePath), new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}

