package Parser;

import BDConnection.BDConnection;
import Entities.Buyers;
import Entities.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.util.ISO8601Utils;
import org.apache.log4j.Logger;
import Constants.ConstStrings;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class Parser {
    /**
     * Метод для парсинга данных из JSON
     * @return
     */
    public static Map<?, ?> fromJson() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\resources\\Input1.json"));
            Map<?, ?> map = gson.fromJson(reader, Map.class);

            reader.close();
            return map;

        } catch (Exception e) {
            LogParser.errorWriter(e.getMessage());
        }
        return null;
    }

    /**
     * Метод для конвертации данных в JSON формат
     * @param result
     */
    public static void toJson(Result result) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(result);

        // Java objects to File
        try (FileWriter writer = new FileWriter("src\\main\\resources\\Output1.json")) {
            gson.toJson(result, writer);
            System.out.println("Првоерьте файл Output1.json");
        } catch (IOException e) {
            LogParser.errorWriter(e.getMessage());
        }
    }

    /**
     * Метод для записи данных из селект запроса lastNameQuery в сущность
     * @param map
     * @return
     */
    public static Result.InnerResult lastNameFilter(Map <String, Object> map) {
        //создаём экземпляр класса Result.InnerResult
        Result.InnerResult lastNameResult = new Result.InnerResult();
        Connection dbConnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbConnection.createStatement();

            String lastNameQuery = ConstStrings.LAST_NAME_QUERY;

            List<Map<String, Object>> criterias = (List<Map<String, Object>>) map.get("criterias");

            for (Map<String, Object> elements : criterias) {
                for (Map.Entry<String, Object> entry : elements.entrySet()) {
                    if (entry.getKey().equals("lastName") || entry.getKey().equals("firstName")) {
                        lastNameQuery += entry.getValue() + "';";
                        // добавляем элементы в мапу
                        lastNameResult.setCriteria((Map<String, Object>) elements);
                    }
                }
            }

            ResultSet resultLastName = statement.executeQuery(lastNameQuery);
            while (resultLastName.next()) {
                String buyerFirstName = resultLastName.getString("firstName");
                String buyerLastName = resultLastName.getString("lastName");
//                добавляем данные в сущность Buyers
                lastNameResult.addResults(new Buyers(buyerFirstName, buyerLastName));
            }

            return lastNameResult;

        } catch (SQLException e) {
            LogParser.errorWriter(e.getMessage());
            return null;
        }
    }

    /**
     * Метод для записи данных из селект запроса productNameResult в сущность
     * @param map
     * @return
     */
    public static Result.InnerResult productNameFilter(Map <String, Object> map) {
        Result.InnerResult productNameResult = new Result.InnerResult();
        Connection dbConnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbConnection.createStatement();

            String productNameQuery = ConstStrings.PRODUCT_NAME_QUERY;

            List<Map<String, Object>> criterias = (List<Map<String, Object>>) map.get("criterias");

            for (Map<String, Object> elements : criterias) {
                for (Map.Entry<String, Object> entry : elements.entrySet()) {
                    if (entry.getKey().equals("productName")) {
                        productNameQuery = productNameQuery.replace("$productName", entry.getValue().toString());
                        productNameResult.setCriteria((Map< String, Object >)elements);
                    }
                    if (entry.getKey().equals("minTimes")) {
                        productNameQuery = productNameQuery.replace("$minTimes", entry.getValue().toString().replace(".0", ""));
                        productNameResult.setCriteria((Map< String, Object >)elements);
                    }
                }
            }

            ResultSet resultProductMinTimes = statement.executeQuery(productNameQuery);
            while (resultProductMinTimes.next()) {
                Integer minTimes = resultProductMinTimes.getInt("counter");
                String productName = resultProductMinTimes.getString("productName");
                String buyerFirstName = resultProductMinTimes.getString("firstName");
                String buyerLastName = resultProductMinTimes.getString("lastName");
                productNameResult.addResults(new Buyers(buyerFirstName, buyerLastName));
            }

            return productNameResult;

        } catch (SQLException e) {
            LogParser.errorWriter(e.getMessage());
            return null;
        }
    }

    /**
     * Метод для записи данных из селект запроса minMaxQuery в сущность
     * @param map
     * @return
     */
    public static Result.InnerResult minMaxFilter(Map <String, Object> map) {
        Result.InnerResult minMaxResult = new Result.InnerResult();
        Connection dbconnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbconnection.createStatement();

            String minMaxQuery = ConstStrings.MIN_MAX_QUERY;

            List<Map<String, Object>> criterias = (List<Map<String, Object>>) map.get("criterias");

            for (Map<String, Object> elements : criterias) {
                for (Map.Entry<String, Object> entry : elements.entrySet()) {
                    if (entry.getKey().equals("minExpenses")) {
                        minMaxQuery = minMaxQuery.replace("$minimum", entry.getValue().toString());
                        minMaxResult.setCriteria((Map<String, Object>) elements);
                    }
                    if (entry.getKey().equals("maxExpenses")) {
                        minMaxQuery = minMaxQuery.replace("$maximum", entry.getValue().toString().replace(".0", ""));
                        minMaxResult.setCriteria((Map<String, Object>) elements);
                    }
                }
            }

            ResultSet resultMinMax = statement.executeQuery(minMaxQuery);
            while (resultMinMax.next()) {
                Integer amount = resultMinMax.getInt("amount");
                String firstname = resultMinMax.getString("firstname");
                String lastname = resultMinMax.getString("lastname");
                minMaxResult.addResults(new Buyers(firstname, lastname));
            }

            return minMaxResult;

        } catch (SQLException e) {
            LogParser.errorWriter(e.getMessage());
            return null;
        }
    }

    /**
     * Метод для записи данных из селект запроса badCustomersQuery в сущность
     * @param map
     * @return
     */
    public static Result.InnerResult badCustomersFilter(Map <String, Object> map) {
        Result.InnerResult badCustomerResult = new Result.InnerResult();
        Connection dbconnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbconnection.createStatement();

            String badCustomersQuery = ConstStrings.BAD_CUSTOMERS_QUERY;

            List<Map<String, Object>> criterias = (List<Map<String, Object>>) map.get("criterias");

            for (Map<String, Object> elements : criterias) {
                for (Map.Entry<String, Object> entry : elements.entrySet()) {
                    if (entry.getKey().equals("badCustomers")) {
                        badCustomersQuery = badCustomersQuery.replace("$amount", entry.getValue().toString());
                        badCustomerResult.setCriteria((Map<String, Object>)elements);
                    }
                }
            }

            ResultSet resultBadCustomers = statement.executeQuery(badCustomersQuery);
            while (resultBadCustomers.next()) {
                String firstname = resultBadCustomers.getString("firstname");
                String lastname = resultBadCustomers.getString("lastname");
                badCustomerResult.addResults(new Buyers(firstname, lastname));
            }
            return badCustomerResult;

        } catch (SQLException e) {
            LogParser.errorWriter(e.getMessage());

            return null;
        }
    }
}






