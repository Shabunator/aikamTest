package Parser;

import BDConnection.BDConnection;
import Entities.Buyers;
import Entities.DateResult;
import Entities.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public static Map<?, ?> fromJson() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\resources\\Input1.json"));
            Map<?, ?> map = gson.fromJson(reader, Map.class);

            reader.close();
            return map;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void toJson(Result result) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(result);

        // Java objects to File
        try (FileWriter writer = new FileWriter("src\\main\\resources\\Output1.json")) {
            gson.toJson(result, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(json);
    }

    public static Result.InnerResult lastNameFilter(Map <String, Object> map) {
        //создаём экземпляр класса Result.InnerResult
        Result.InnerResult lastNameResult = new Result.InnerResult();
        Connection dbConnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbConnection.createStatement();

            String lastNameQuery = "SELECT * FROM Buyers WHERE lastName = '";

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
//                System.out.printf("%s %s", buyerFirstName, buyerLastName);
//                добавляем данные в сущность Buyers
                lastNameResult.addResults(new Buyers(buyerFirstName, buyerLastName));
            }
//            System.out.println(lastNameResult);

            return lastNameResult;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Result.InnerResult productNameFilter(Map <String, Object> map) {
        Result.InnerResult productNameResult = new Result.InnerResult();
        Connection dbConnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbConnection.createStatement();

            String productNameQuery = "SELECT COUNT(productid) AS counter, products.productname, buyers.firstname, buyers.lastname\n" +
                    "FROM products\n" +
                    "JOIN purchases ON products.id = purchases.productid\n" +
                    "JOIN buyers ON buyers.id = purchases.buyerid\n" +
                    "WHERE products.productname = '$productName' \n" +
                    "GROUP BY products.productname, purchases.buyerid, buyers.firstname, buyers.lastname\n" +
                    "HAVING COUNT(productid) = '$minTimes'";

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
                System.out.printf("%d %s %s %s \n \n", minTimes, productName, buyerFirstName, buyerLastName);
                productNameResult.addResults(new Buyers(buyerFirstName, buyerLastName));
            }

            System.out.println(productNameResult);

            return productNameResult;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Result.InnerResult minMaxFilter(Map <String, Object> map) {
        Result.InnerResult minMaxResult = new Result.InnerResult();
        Connection dbconnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbconnection.createStatement();

            String minMaxQuery = "SELECT SUM(price) AS amount, buyers.firstname, buyers.lastname\n" +
                    "FROM products\n" +
                    "JOIN purchases ON products.id = purchases.productid\n" +
                    "JOIN buyers ON buyers.id = purchases.buyerid\n" +
                    "GROUP BY buyers.firstname, buyers.lastname\n" +
                    "HAVING SUM(price) BETWEEN $minimum AND $maximum";

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
                System.out.printf("%d %s %s \n \n", amount, firstname, lastname);
                minMaxResult.addResults(new Buyers(firstname, lastname));
            }

            System.out.println(minMaxResult);

            return minMaxResult;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Result.InnerResult badCustomersFilter(Map <String, Object> map) {
        Result.InnerResult badCustomerResult = new Result.InnerResult();
        Connection dbconnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbconnection.createStatement();

            String badCustomersQuery = "SELECT COUNT(buyerid), buyers.firstname, buyers.lastname\n" +
                    "\n" +
                    "FROM purchases\n" +
                    "JOIN buyers ON purchases.buyerid = buyers.id\n" +
                    "GROUP BY buyerid, buyers.firstname, buyers.lastname\n" +
                    "\n" +
                    "HAVING COUNT (buyerid) = (SELECT COUNT(buyerid)\n" +
                    "FROM purchases\n" +
                    "GROUP BY buyerid\n" +
                    "ORDER BY COUNT(buyerid)\n" +
                    "LIMIT 1\n" +
                    ")\n" +
                    "\n" +
                    "LIMIT $amount";

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
                System.out.printf("%s %s \n", firstname, lastname);
                badCustomerResult.addResults(new Buyers(firstname, lastname));
            }
            System.out.println(badCustomerResult);

            return badCustomerResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}






