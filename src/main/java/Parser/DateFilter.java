package Parser;

import BDConnection.BDConnection;
import Entities.Result;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class DateFilter {

    public static final Logger log = Logger.getLogger(BDConnection.class);

    public static Map<String, String> fromJson() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\resources\\Input2.json"));
            Map<String, String> map = gson.fromJson(reader, Map.class);
            reader.close();

            return map;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void execute(Map<String, String> map) throws ParseException {
        /**
         * 0 - воскр
         * 1 - понедельник
         * 2- вторинк
         * 3 - среда
         * 4 - четверг
         * 5 - пятница
         * 6 - суббота
         * @throws ParseException
         */
        String startDateStr = map.get("startDate");
        String endDateStr = map.get("endDate");
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

//        System.out.println(date1);

        LocalDate startDate = LocalDate.of(date1.getYear() + 1900, date1.getMonth() + 1, date1.getDate()); //год, мес, день
        LocalDate endDate = LocalDate.of(date2.getYear() + 1900, date2.getMonth() + 1, date2.getDate());

        int workdays = 0;
        if (startDate.isEqual(endDate)) {
            return;
        }

        while (startDate.isBefore(endDate) || startDate.equals(endDate)) {
            if (DayOfWeek.MONDAY.equals(startDate.getDayOfWeek())
                    || DayOfWeek.THURSDAY.equals(startDate.getDayOfWeek())
                    || DayOfWeek.WEDNESDAY.equals(startDate.getDayOfWeek())
                    || DayOfWeek.TUESDAY.equals(startDate.getDayOfWeek())
                    || DayOfWeek.FRIDAY.equals(startDate.getDayOfWeek())) {
                workdays++;
            }
            startDate = startDate.plusDays(1);
        }
    }

    public static void dateResult(Map<String, String> map) {
        //создаём экземпляр класса Result.InnerResult
        Connection dbConnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbConnection.createStatement();

            String dateQuery = "SELECT distinct buyers.firstname, buyers.lastname, products.productname, sum(products.price) as price, products.id \n" +
                    "FROM purchases \n" +
                    "inner JOIN products ON purchases.productid = products.id\n" +
                    "inner JOIN buyers ON purchases.buyerid = buyers.id \n" +
                    "WHERE purchases.date BETWEEN '$startDate' AND '$endDate' and extract('ISODOW' from date) < 6\n" +
                    "group by buyers.firstname, buyers.lastname, products.productname,products.id\n" +
                    "order by sum(products.price) DESC;";

            dateQuery = dateQuery.replace("$startDate", map.get("startDate"));
            dateQuery = dateQuery.replace("$endDate", map.get("endDate"));

            ResultSet resultSet = statement.executeQuery(dateQuery);

            while (resultSet.next()) {
                String buyerFirstName = resultSet.getString("firstName");
                String buyerLastName = resultSet.getString("lastName");
                String productName = resultSet.getString("productName");
                Integer price = resultSet.getInt("price");

//                System.out.printf("%s %s %s %d \n \n", buyerFirstName, buyerLastName, productName, price);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}