import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Jake on 4/27/17.
 */
public class SQL {


    public static ArrayList viewUsers(Connection conn) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("select * from users");
        while (results.next()) {
            Integer id = results.getInt("id");
            String name = results.getString("username");
            users.add(new User(id, name));
        }
        return users;
    }
    public static void createUser(Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("insert into users values (null, ?)");
        stmt.setString(1, username);
        stmt.execute();
        }

    public static void addItems(Connection conn, Cart items) throws SQLException { //adds items to order
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("update order set user");

    }
    public static void selectUser(Connection conn, String testName) throws SQLException { //checks user vs database
        ArrayList<Order> orders = new Order();
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("select ? from users.name");
        stmt.setString(1, testName);


        String name = result.getString("name");
        Integer order = result.getInt("order");
    }
    public static ArrayList total(Connection conn, String order) throws SQLException{ //get items of order
        ArrayList all = new ArrayList();
        Statement stmt=conn.createStatement();
        ResultSet r = stmt.executeQuery("select * from orders inner join users on users.username = orders.owner");
        ResultSet results = stmt.executeQuery("select * from items inner join orders on " +
                "items.order_ID=orders.order_ID");
        ResultSet results2 = stmt.executeQuery("select * from items where items.name = ?");
        ResultSet results3 = stmt.executeQuery("select * from items where items.active = true");

        while(results3.next()) {
            String name = results3.getString("item_name");
            String cost = results3.getString("item_cost");
            String quanity = results3.getString("item_quantity");
            all.add(new Item(name, Double.valueOf(cost), Double.valueOf(quanity)));
        }
    return all;
    }
    public static void completeOrder(Connection conn, String order) throws SQLException { //mark order as complete

    }

}

