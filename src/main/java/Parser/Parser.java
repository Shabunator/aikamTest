package Parser;

import BDConnection.BDConnection;
import Entities.Buyers;
import Entities.Products;
import Entities.Purchases;
import com.google.gson.Gson;

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
}






