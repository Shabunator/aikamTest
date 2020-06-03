package Parser;

import BDConnection.BDConnection;
import Entities.DateResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;


public class DateFilter {
    /**
     * Метод для парсинга из JSON
     * @return
     */
    public static Map<String, String> fromJson() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\resources\\Input2.json"));
            Map<String, String> map = gson.fromJson(reader, Map.class);
            reader.close();
            return map;

        } catch (Exception e) {
            LogParser.errorWriter(e.getMessage());
        }
        return null;
    }

    /**
     * Метод для конвертировании данных в JSON формат
     * @param dateResult
     */
    public static void toJson(DateResult dateResult) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(dateResult);

        // Java objects to File
        try (FileWriter writer = new FileWriter("src\\main\\resources\\Output2.json")) {
            gson.toJson(dateResult, writer);
        } catch (IOException e) {
            LogParser.errorWriter(e.getMessage());
        }
        System.out.println("Проверьте файл Output2.json");
    }

    /**
     * Метод для подсчета колличества не рабочих дней. Испольуется в сущности DateResult
     * @param map
     * @return
     */
    public static Integer execute(Map<String, String> map) {

        String startDateStr = map.get("startDate");
        String endDateStr = map.get("endDate");

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
        } catch (ParseException e) {
            LogParser.errorWriter(e.getMessage());
        }

        LocalDate startDate = LocalDate.of(date1.getYear() + 1900, date1.getMonth() + 1, date1.getDate());
        LocalDate endDate = LocalDate.of(date2.getYear() + 1900, date2.getMonth() + 1, date2.getDate());

        int workdays = 0;

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
        return workdays;
    }

    /**
     * Метод для записи данных из селект запроса dateQuery в сущность
     * @param map
     */
    public static void dateResult(Map<String, String> map) {
        Connection dbConnection = BDConnection.getDBConnection();
        try {
            Statement statement = dbConnection.createStatement();

            String dateQuery = ConstStrings.DATE_QUERY;

            dateQuery = dateQuery.replace("$startDate", map.get("startDate"));
            dateQuery = dateQuery.replace("$endDate", map.get("endDate"));

            ResultSet resultSet = statement.executeQuery(dateQuery);

            //слздание экземпляра класса DateResult
            DateResult dateResult = new DateResult();

            while (resultSet.next()) {
                String buyerFirstName = resultSet.getString("firstName");
                String buyerLastName = resultSet.getString("lastName");
                String productName = resultSet.getString("productName");
                Integer price = resultSet.getInt("price");

                //наполнение сущности DateResult
                dateResult.fillObject(buyerFirstName + " " + buyerLastName, productName, price);
            }

            toJson(dateResult);
        } catch (SQLException e) {
            LogParser.errorWriter(e.getMessage());
        }
    }

    /**
     * Метод для записи данных из селект запроса totalExpenses в сущность
     * @param map
     * @return
     */
    public static Integer totalExpensesResult(Map<String, String> map) {
        Connection dbConneection = BDConnection.getDBConnection();
        Integer productTotalExpenses = 0;
        try {
            Statement statement = dbConneection.createStatement();

            String totalExpenses = ConstStrings.TOTAL_EXPENSES;

            totalExpenses = totalExpenses.replace("$startDate", map.get("startDate"));
            totalExpenses = totalExpenses.replace("$endDate", map.get("endDate"));

            ResultSet resultTotalExpenses = statement.executeQuery(totalExpenses);

            resultTotalExpenses.next();
            productTotalExpenses = resultTotalExpenses.getInt("price");

        } catch (SQLException e) {
            LogParser.errorWriter(e.getMessage());
        }
        return productTotalExpenses;
    }

    /**
     * Метод для записи данных из селект запроса avgExpenses в сущность
     * @param map
     * @return
     */
    public static Double avgExpensesResult(Map<String, String> map) {
        Connection dbConnection = BDConnection.getDBConnection();
        Double productAvgExpenses = 0.0;
        try {
            Statement statement = dbConnection.createStatement();

            String avgExpenses = ConstStrings.AVG_EXPENSES;

            avgExpenses = avgExpenses.replace("$startDate", map.get("startDate"));
            avgExpenses = avgExpenses.replace("$endDate", map.get("endDate"));

            ResultSet resultAvgExpenses = statement.executeQuery(avgExpenses);

            resultAvgExpenses.next();
            productAvgExpenses = resultAvgExpenses.getDouble("avg");

        } catch (SQLException e) {
            LogParser.errorWriter(e.getMessage());
        }
        return productAvgExpenses;
    }
}
