import org.h2.tools.Server;
import spark.Spark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Jake on 4/27/17.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        Spark.staticFileLocation("/public");
        Server.createWebServer().start();
        //port(Integer.valueOf(System.getenv("PORT")));
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();

        stmt.execute("create table if not exists users (id identity, username varchar)");
        stmt.execute("create table if not exists items (order_id identity, name varchar, cost double, quantity double)");
        stmt.execute("create table if not exists orders (user_id identity, quantity double, amount double)");

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("name");
                    ArrayList<User> userCheck = SQL.viewUsers(conn);
                    User currentUser = null;
                    for (User a : userCheck) {
                        if (a.username.equals(name)) {
                            currentUser = a;
                            break;
                        } else {
                            SQL.createUser(conn, name);
                        }
                    }
                    return "";
                })
        );
        Spark.post(
                "/add-to-cart", //adds items to order
                ((request, response) -> {
                    String itemName = request.queryParams("itemName");
                    String itemCost = request.queryParams("itemCost");
                    String itemQuantity = request.queryParams("itemQuantity");
                    return "";
                })
        );
        Spark.get(
                "/checkout", //show user data and detail of over
                ((request, response) -> {

                    return "";
                })
        );
        Spark.post(
                "/checkout", //process order, mark order as complete
                ((request, response) -> {

                    return "";
                })
        );


    }
}
