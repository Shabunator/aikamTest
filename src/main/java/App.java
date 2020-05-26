import BDConnection.BDConnection;
import Entities.Result;
import Parser.Parser;
import Parser.DateFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class App {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws SQLException, ParseException {
        BDConnection.createDbUserTable();
        DateFilter.execute();
//        Map<String, Object> result = (Map<String, Object>) Parser.fromJson();
//        Result.InnerResult resultLastNameFilter = Parser.lastNameFilter(result);
//        Result.InnerResult resultProductNameFilter = Parser.productNameFilter(result);
//        Result.InnerResult resultMinMaxFilter = Parser.minMaxFilter(result);
//        Result.InnerResult resultBadCustomersFilter = Parser.badCustomersFilter(result);
//
//        List<Result.InnerResult> resultList = new ArrayList<>();
//        resultList.add(resultLastNameFilter);
//        resultList.add(resultProductNameFilter);
//        resultList.add(resultMinMaxFilter);
//        resultList.add(resultBadCustomersFilter);
//
//        Result resultSet = new Result(resultList);
//        Parser.toJson(resultSet);
    }
}
