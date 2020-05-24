package BDConnection;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class BDConnection {
    public static final Logger log = Logger.getLogger(BDConnection.class);

    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/test";
    static final String USER = "postgres";
    static final String PASS = "postgres";

    public static Connection getDBConnection() {
        Connection dbConnection = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            log.info(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(DB_URL,USER,PASS);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.info(e.getMessage());
        }
        return dbConnection;
    }

    public static void createDbUserTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            FileReader reader = new FileReader("src/main/resources/SQL.txt");
            Scanner scanner = new Scanner(reader);
            StringBuilder SQL = new StringBuilder();
            while (scanner.hasNext()) {
                SQL.append(scanner.nextLine());
            }

            // выполнить SQL запрос
            statement.execute(String.valueOf(SQL));

            System.out.println("Table \"Buyers\" is created!\n" +
                    "Table \"Products\" is created!\n" +
                    "Table \"Purchases\" is created!\n");
            log.info("Tables are created");

        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());

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

