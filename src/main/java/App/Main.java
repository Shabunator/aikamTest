package App;

import BDConnection.BDConnection;
import Entities.Result;
import Parser.Parser;
import Parser.DateFilter;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        BDConnection.createDbUserTable();
        DateFilter.dateResult(DateFilter.fromJson());

        Map<String, Object> result = (Map<String, Object>) Parser.fromJson();
        // использование анонимного объекта с инициализацией
        Result resultSet = new Result(new ArrayList<Result.InnerResult>(){
            {
                add(Parser.lastNameFilter(result));
                add(Parser.productNameFilter(result));
                add(Parser.minMaxFilter(result));
                add(Parser.badCustomersFilter(result));
            }
        });
        Parser.toJson(resultSet);
    }
}
