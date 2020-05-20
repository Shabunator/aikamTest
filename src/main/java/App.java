import BDConnection.BDConnection;
import Parser.Parser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.awt.image.ImageProducer;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

public class App {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws SQLException {
        BDConnection.createDbUserTable();
        Parser.productNameFilter((Map<String, Object>) Parser.fromJson());


    }
}
