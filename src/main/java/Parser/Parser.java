package Parser;

import BDConnection.BDConnection;
import Entities.Buyers;
import Entities.Products;
import Entities.Purchases;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.plaf.nimbus.State;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {



    public static Map<?, ?> fromJson() {
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\home\\Desktop\\JavaProjects\\aikamTest\\src\\main\\resources\\Input.json"));

            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);

            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                //System.out.println(entry.getKey() + "=" + entry.getValue());
            }
            // close reader
            reader.close();

            return map;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void execute(Map <String, Object> map) {
        Connection dbConnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbConnection.createStatement();
            String query = "SELECT * FROM Buyers WHERE ";
            List<Map<String, Object>> criterias = (List<Map<String, Object>>) map.get("criterias");

            for (Map<String, Object> elements : criterias) {
                for (Map.Entry<String, Object> entry : elements.entrySet() ) {
                    //System.out.println(entry.getKey() + " = " + entry.getValue());
                    if (entry.getKey().equals("lastName") || entry.getKey().equals("firstName")) {
                        query += entry.getKey() + " = '" + entry.getValue() + "' AND ";
                    }
                }
            }

            query = query.substring(0, query.length()-4) + ";";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println(query);

            ArrayList<Buyers> arrayList = new ArrayList<>();

            while (resultSet.next()) {
                String lastName = resultSet.getString("firstName");
                String firstName = resultSet.getString("firstName");
                Integer id = resultSet.getInt("id");

                arrayList.add(new Buyers(id, lastName, firstName));

            }

            ArrayList<Integer> productIds = new ArrayList<>();

            for (Buyers buyers:
                 arrayList) {

                String pushProducts = "SELECT * FROM Purchases WHERE buyerId = " + buyers.getId();

                ResultSet resultProducts = statement.executeQuery(pushProducts);
                while (resultProducts.next()) {
                    Integer productId = resultProducts.getInt("productId");
                    System.out.println(productId);

                    productIds.add(productId);
                }
            }

            ArrayList<Products> purchasesList = new ArrayList<>();

            for (Integer id:
                 productIds) {

                String pushPurchaes = "SELECT * FROM Products WHERE Id = " + id;

                ResultSet resultPurchases = statement.executeQuery(pushPurchaes);
                while (resultPurchases.next()) {
                    String productName = resultPurchases.getString("productName");
                    Double price = resultPurchases.getDouble("price");
                    purchasesList.add(new Products(productName, price));
                }
            }

            System.out.println(productIds);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void lastNameFilter(Map <String, Object> map) {

        Connection dbConnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbConnection.createStatement();

            String lastNameQuery = "SELECT * FROM Buyers WHERE lastName = '";

            List<Map<String, Object>> criterias = (List<Map<String, Object>>) map.get("criterias");

            for (Map<String, Object> elements : criterias) {
                for (Map.Entry<String, Object> entry : elements.entrySet()) {
                    //System.out.println(entry.getKey() + " = " + entry.getValue());
                    if (entry.getKey().equals("lastName") || entry.getKey().equals("firstName")) {
                        lastNameQuery += entry.getValue() + "';";
                    }
                }
            }

            ResultSet resultLastName = statement.executeQuery(lastNameQuery);
            while (resultLastName.next()) {
                String buyerFirstName = resultLastName.getString("firstName");
                String buyerLastName = resultLastName.getString("lastName");
                System.out.printf("%s %s", buyerFirstName, buyerLastName);
            }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    public static void productNameFilter(Map <String, Object> map) {
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
                    //System.out.println(entry.getKey() + " = " + entry.getValue());
                    if (entry.getKey().equals("productName")) {
                        productNameQuery = productNameQuery.replace("$productName", entry.getValue().toString());
                    }
                    if (entry.getKey().equals("minTimes")) {
                        productNameQuery = productNameQuery.replace("$minTimes", entry.getValue().toString().replace(".0", ""));
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void minMaxFilter(Map <String, Object> map) {
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
                    }
                    if (entry.getKey().equals("maxExpenses")) {
                        minMaxQuery = minMaxQuery.replace("$maximum", entry.getValue().toString().replace(".0", ""));
                    }
                }
            }

            ResultSet resultMinMax = statement.executeQuery(minMaxQuery);
            while (resultMinMax.next()) {
                Integer amount = resultMinMax.getInt("amount");
                String firstname = resultMinMax.getString("firstname");
                String lastname = resultMinMax.getString("lastname");
                System.out.printf("%d %s %s \n \n", amount, firstname, lastname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void badCustomersFilter(Map <String, Object> map) {
        Connection dbconnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbconnection.createStatement();

            String badCustumersQuery = "SELECT COUNT(buyerid), buyers.firstname, buyers.lastname\n" +
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
                        badCustumersQuery = badCustumersQuery.replace("$amount", entry.getValue().toString());
                    }
                }
            }

            ResultSet resultBadCustomers = statement.executeQuery(badCustumersQuery);
            while (resultBadCustomers.next()) {
                String firstname = resultBadCustomers.getString("firstname");
                String lastname = resultBadCustomers.getString("lastname");
                System.out.printf("%s %s \n", firstname, lastname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}






