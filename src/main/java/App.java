import BDConnection.BDConnection;
import Entities.Result;
import Parser.Parser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.*;

public class App {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws SQLException {
        BDConnection.createDbUserTable();
        Result.InnerResult result1 = Parser.lastNameFilter((Map<String, Object>) Parser.fromJson());
        Result.InnerResult result2 = Parser.productNameFilter((Map<String, Object>) Parser.fromJson());
        Result.InnerResult result3 = Parser.minMaxFilter((Map<String, Object>) Parser.fromJson());
        Result.InnerResult result4 = Parser.badCustomersFilter((Map<String, Object>) Parser.fromJson());

        List<Result.InnerResult> resultList = new ArrayList<>();
        resultList.add(result1);
        resultList.add(result2);
        resultList.add(result3);
        resultList.add(result4);

        Result resultSet = new Result(resultList);
        Parser.toJson(resultSet);
    }
}
