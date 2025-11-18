package screener.utils;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import java.io.File;

public class JsonFileReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode read(String path) {
        try {
            return mapper.readTree(new File(path));
        } catch (Exception e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
            return null;
        }
    }
}
