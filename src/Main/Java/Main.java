import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Session;
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
                " item_quantity double, order_id int)");
        stmt.execute("create table if not exists orders (order_id identity, owner varchar, active_order boolean)");

        Spark.post(
                "/login", //check database for login. If return null, create new user. Go to last active order.
                ((request, response) -> {
                    String name = request.queryParams("name");
                    Session session = request.session(); //requests session, if non exists, it will make a new one
                    //this session is stored in a cookie and can store a value.
                    //we can call this as many times as we want and it wont remake a session until the session is
                    //invalid.
                    User u = SQL.selectUser(conn, name);

                    if (u == null) {
                        SQL.createUser(conn, name);
                    }
                    session.attribute("username", name);
                    //we are storing the value of "username" inside the cookie
                    //of session and passing it the name provided by the user.
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/add-to-cart", //adds items to order
                ((request, response) -> {
                    String itemName = request.queryParams("itemName");
                    String itemCost = request.queryParams("itemCost");
                    String itemQuantity = request.queryParams("itemQuantity");
                    Session session = request.session();
                    String name = session.attribute("username");
                    Integer order = SQL.checkCart(conn,name);
                    Item item = new Item(itemName, Double.valueOf(itemCost), Double.valueOf(itemQuantity), order);
                    SQL.addItems(conn, item);
                    return "";
                })
        );
        Spark.get(
                "/checkout", //show user data and detail of order
                ((request, response) -> {
                    Session session = request.session(); //makes session object
                    String currentUser = session.attribute("username"); //gets object stored in session cookie.

                    HashMap m = new HashMap();
                    m.put("all", SQL.total(conn, currentUser)); //inserts session cookie name into sql command.
                    return new ModelAndView(m, "checkout.html");

                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/checkout", //process order, mark order as complete
                ((request, response) -> {
                    Session session = request.session();
                    String currentUser = session.attribute("username"); //gets current user as a string
                    SQL.completeOrder(conn, currentUser); //marks order boolean complete = true
                    session.invalidate(); //logs out session of user.
                    response.redirect("/");
                    return "";
                })
        );
        Spark.get(
                "/",
                ((request, response) -> {
                    String currentUser = request.session().attribute("username");
                    if (currentUser == null) {
                        response.redirect("login-page.html");
                    }
                    HashMap m = new HashMap();
                    return new ModelAndView(m, "home.html");

                }),
                new MustacheTemplateEngine()
        );


    }
}
