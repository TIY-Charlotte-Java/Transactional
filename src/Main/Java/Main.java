import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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
        stmt.execute("create table if not exists items (order_id identity, item_name varchar, item_cost double," +
                " item_quantity double, order_name varchar)");
        stmt.execute("create table if not exists orders (order_id identity, owner varchar, active_order boolean)");

        Spark.post(
                "/login", //check database for login. If return null, create new user. Go to last active order.
                ((request, response) -> {
                    String name = request.queryParams("name");

                    return "";
                })
        );
        Spark.post(
                "/add-to-cart", //adds items to order
                ((request, response) -> {
                    String itemName = request.queryParams("itemName");
                    String itemCost = request.queryParams("itemCost");
                    String itemQuantity = request.queryParams("itemQuantity");
                    Item item = new Item(currentOrder, itemName, Double.valueOf(itemCost), Double.valueOf(itemQuantity));
                    SQL.addItems(conn, item);
                    return "";
                })
        );
        Spark.get(
                "/checkout", //show user data and detail of order
                ((request, response) -> {
                    HashMap m = new HashMap();
                    m.put("all", SQL.total(conn, currentUser));
                    return new ModelAndView(m, "checkout.html");

                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/checkout", //process order, mark order as complete
                ((request, response) -> {
                    System.out.println("making order is in-active");
                    SQL.completeOrder(conn, currentUser);
                    return "";
                })
        );


    }
}
