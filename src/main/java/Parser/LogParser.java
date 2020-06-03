package Parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Метод для парсинга стандартного вывода в файл данных из Log4j в формат JSON
 */
public class LogParser {
    public static void errorWriter(String errorText) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Map<String, String> logTipe = new HashMap<>();
        logTipe.put("type", "error");
        logTipe.put("messege", errorText);

        // Java objects to File
        try (FileWriter writer = new FileWriter("src\\main\\resources\\log4j\\log_file.json", true)) {
            gson.toJson(logTipe, writer);
        } catch (IOException e) {
            LogParser.errorWriter(e.getMessage());
        }
    }
}
