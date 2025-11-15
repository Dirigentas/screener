package screener.utils;

import tools.jackson.databind.ObjectMapper;
import java.io.File;

public class JsonFileWriter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void write(String path, Object data) {
        try {
            // 1. Create a File object for the full path
            File outputFile = new File(path);

            // 2. Get the parent directory path
            File parentDir = outputFile.getParentFile();

            // 3. Check if the parent directory exists and create it if not
            if (parentDir != null && !parentDir.exists()) {
                // Use mkdirs() to create all necessary parent directories
                parentDir.mkdirs();
            }
            // 4. Write the JSON data to the file
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(path), data);
                  
        } catch (Exception e) {
            System.out.println("Error writing JSON file: " + e.getMessage());
        }
    }
}

