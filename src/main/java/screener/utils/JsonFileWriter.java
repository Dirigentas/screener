package screener.utils;

// import com.fasterxml.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectMapper;
import java.io.File;

public class JsonFileWriter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void write(String path, Object data) {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(path), data);
        } catch (Exception e) {
            System.out.println("Error writing JSON file: " + e.getMessage());
        }
    }
}

