package Constants;

public class ConstStrings {

    /*                      Константы используемые в классе BDConnection                      */
    public static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/test";
    public static final String USER = "postgres";
    public static final String PASS = "postgres";

    /*                      Константы используемые в классе Parser                      */
    public static final String LAST_NAME_QUERY = "SELECT * FROM Buyers WHERE lastName = '";

    public static final String PRODUCT_NAME_QUERY = "SELECT COUNT(productid) AS counter, products.productname, buyers.firstname, buyers.lastname\n" +
            "FROM products\n" +
            "JOIN purchases ON products.id = purchases.productid\n" +
            "JOIN buyers ON buyers.id = purchases.buyerid\n" +
            "WHERE products.productname = '$productName' \n" +
            "GROUP BY products.productname, purchases.buyerid, buyers.firstname, buyers.lastname\n" +
            "HAVING COUNT(productid) = '$minTimes'";

    public static final String MIN_MAX_QUERY = "SELECT SUM(price) AS amount, buyers.firstname, buyers.lastname\n" +
            "FROM products\n" +
            "JOIN purchases ON products.id = purchases.productid\n" +
            "JOIN buyers ON buyers.id = purchases.buyerid\n" +
            "GROUP BY buyers.firstname, buyers.lastname\n" +
            "HAVING SUM(price) BETWEEN $minimum AND $maximum";

    public static final String BAD_CUSTOMERS_QUERY= "SELECT COUNT(buyerid), buyers.firstname, buyers.lastname " +
            "FROM purchases\n" +
            "JOIN buyers ON purchases.buyerid = buyers.id\n" +
            "GROUP BY buyerid, buyers.firstname, buyers.lastname\n" +
            "HAVING COUNT (buyerid) = (SELECT COUNT(buyerid)\n" +
            "FROM purchases\n" +
            "GROUP BY buyerid\n" +
            "ORDER BY COUNT(buyerid)\n" +
            "LIMIT 1" +
            ") " +
            "LIMIT $amount";

    /*                      Константы используемые в классе DateFilter                      */
    public static final String DATE_QUERY = "SELECT distinct buyers.firstname, buyers.lastname, products.productname, sum(products.price) as price\n" +
            "FROM purchases \n" +
            "inner JOIN products ON purchases.productid = products.id\n" +
            "inner JOIN buyers ON purchases.buyerid = buyers.id \n" +
            "WHERE purchases.date BETWEEN '$startDate' AND '$endDate' and extract('ISODOW' from date) < 6\n" +
            "group by buyers.firstname, buyers.lastname, products.productname\n" +
            "order by sum(products.price) DESC;";

    public static final String TOTAL_EXPENSES = "select sum(products.price) as price\n" +
            "from purchases \n" +
            "inner join products on purchases.productid = products.id  \n" +
            "inner join buyers on purchases.buyerid = buyers.id \n" +
            "where purchases.\"date\" BETWEEN '$startDate' AND '$endDate' and extract('ISODOW' from date) < 6";

    public static final String AVG_EXPENSES = "select avg(price)\n" +
            "from purchases \n" +
            "inner join products on purchases.productid = products.id  \n" +
            "inner join buyers on purchases.buyerid = buyers.id \n" +
            "where purchases.\"date\" BETWEEN '$startDate' AND '$endDate' and extract('ISODOW' from date) < 6\n";
}
