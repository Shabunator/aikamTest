package BDConnection;

import Constants.ConstStrings;
import Parser.LogParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class BDConnection {
    static String db_url = ConstStrings.DB_URL;
    static String user = ConstStrings.USER;
    static String password = ConstStrings.PASS;

    /**
     * Метод для подключение к базе данных
     * @return
     */
    public static Connection getDBConnection() {
        Connection dbConnection = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            LogParser.errorWriter(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(db_url,user,password);
            return dbConnection;
        } catch (SQLException e) {
            LogParser.errorWriter(e.getMessage());
        }
        return dbConnection;
    }

    /**
     * Метод для создания таблиц
     * @throws SQLException
     */
    public static void createDbUserTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            FileReader reader = new FileReader("src/main/resources/SQL1.txt");
            Scanner scanner = new Scanner(reader);
            StringBuilder SQL = new StringBuilder();
            while (scanner.hasNext()) {
                SQL.append(scanner.nextLine());
            }

            // выполнить SQL запрос
            statement.execute(String.valueOf(SQL));

        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            LogParser.errorWriter(e.getMessage());

        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }
}

