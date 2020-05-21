package BDConnection;

import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


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

            String createTables = "DROP TABLE IF EXISTS Buyers CASCADE;\n" +
                    "CREATE TABLE Buyers\n" +
                    "(\n" +
                    "    Id SERIAL PRIMARY KEY,\n" +
                    "    firstName VARCHAR(100),\n" +
                    "    lastName VARCHAR(100)\n" +
                    ");\n" +
                    "DROP TABLE IF EXISTS Products CASCADE;\n" +
                    "CREATE TABLE Products\n" +
                    "(\t\n" +
                    "\tId SERIAL PRIMARY KEY,\n" +
                    "    productName VARCHAR(100),\n" +
                    "    price DECIMAL\n" +
                    ");\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS Purchases CASCADE;\n" +
                    "CREATE TABLE Purchases\n" +
                    "(\n" +
                    "    Id SERIAL PRIMARY KEY,\n" +
                    "    buyerId INTEGER,\n" +
                    "    productId INTEGER,\n" +
                    "    \"date\" timestamp,\n" +
                    "    FOREIGN KEY (BuyerId) REFERENCES Buyers (Id),\n" +
                    "    FOREIGN KEY (ProductId) REFERENCES Products (Id)\n" +
                    ");\n";

            String insertIntoTables = "INSERT INTO Buyers(\n" +
                    "\t\"firstname\", \"lastname\")\n" +
                    "\tVALUES ('Иван', 'Иванов'),\n" +
                    "\t ('Дмитрий', 'Сидоров'),\n" +
                    "\t ('Генадий', 'Зайцев'),\n" +
                    "\t ('Олег', 'Сизов'),\n" +
                    "\t ('Андрей', 'Семёнов'),\n" +
                    "\t ('Александор', 'Пушкин'),\n" +
                    "\t ('Зоя', 'Сильнова'),\n" +
                    "\t ('Артём', 'Усталов'),\n" +
                    "\t ('Павел', 'Незнаев'),\n" +
                    "\t ('Артём', 'Думов'),\n" +
                    "\t ('Ноути', 'Догов'),\n" +
                    "\t ('Харт', 'Стоунов'),\n" +
                    "\t ('Павел', 'Имбирёв'),\n" +
                    "\t ('Глеб', 'Золотов'),\n" +
                    "\t ('Арсений', 'Повелительев'),\n" +
                    "\t ('Серафим', 'Трататаев'),\n" +
                    "\t ('Генадий', 'Огнестрелов'),\n" +
                    "\t ('Екатерина', 'Большеразмерова'),\n" +
                    "\t ('Ксения', 'Сабчак'),\n" +
                    "\t ('Владим', 'Тутин');\n" +
                    "\n" +
                    "INSERT INTO \"products\"(\n" +
                    "\t\"productname\", \"price\")\n" +
                    "\tVALUES ('Хлеб', 34),\n" +
                    "\t('Масло', 102),\n" +
                    "\t('Молоко', 75),\n" +
                    "\t('Сок Яблочный', 69),\n" +
                    "\t('Сок Апельсиновый', 83),\n" +
                    "\t('Вафли', 41),\n" +
                    "\t('Мука', 54),\n" +
                    "\t('Чёрный хлеб', 25),\n" +
                    "\t('Тушёнка', 54),\n" +
                    "\t('Яблоки', 230),\n" +
                    "\t('Апельсины', 195),\n" +
                    "\t('Сгущёнка', 80),\n" +
                    "\t('Имбирь', 2790),\n" +
                    "\t('Лимон', 460),\n" +
                    "\t('Мороженное пломбир', 70),\n" +
                    "\t('Кофе растворимый', 100),\n" +
                    "\t('Кофе молотый', 300),\n" +
                    "\t('Чай черный', 100),\n" +
                    "\t('Чай зеленый', 130),\n" +
                    "\t('Творог', 115),\n" +
                    "\t('Чипсы', 83),\n" +
                    "\t('Вино', 1150),\n" +
                    "\t('Зефир', 160),\n" +
                    "\t('Печенье', 42),\n" +
                    "\t('Маслины', 74),\n" +
                    "\t('Оливки', 86),\n" +
                    "\t('Фасоль', 56),\n" +
                    "\t('Сахар', 74),\n" +
                    "\t('Соль', 49),\n" +
                    "\t('Гречка', 206),\n" +
                    "\t('Минеральная вода', 75);\n" +
                    "\n" +
                    "INSERT INTO purchases(buyerid, productid, date) VALUES (4, 10, localtimestamp);\n" +
                    "INSERT INTO purchases(buyerid, productid, date) VALUES (1, 31, localtimestamp);\n" +
                    " \n";

            // выполнить SQL запрос
            statement.execute(createTables);
            statement.execute(insertIntoTables);

            System.out.println("Table \"Buyers\" is created!\n" +
                    "Table \"Products\" is created!\n" +
                    "Table \"Purchases\" is created!\n");
            log.info("Tables are created");

        } catch (SQLException e) {
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

