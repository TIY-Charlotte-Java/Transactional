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

    public static void addItems(Connection conn, Item items) throws SQLException { //adds items to order
        PreparedStatement stmt = conn.prepareStatement("insert into items values(null,?,?,?,?)");
        stmt.setString(1 , items.itemName);
        stmt.setDouble(2 , items.itemCost);
        stmt.setDouble(3 , items.itemQuantity);
        stmt.setString(4 , items.orderName);
    }

    public static String selectUser(Connection conn, String testName) throws SQLException { //checks user vs database
        PreparedStatement stmt = conn.prepareStatement("select column 2 where username = ?");
        stmt.setString(1 , testName);
        ResultSet results = stmt.executeQuery();
        return results;
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
            String quantity = results3.getString("item_quantity");
            all.add(new Item(name, Double.valueOf(cost), Double.valueOf(quantity)));
        }
        return all;
    }

    public static void completeOrder(Connection conn, String orderOwner) throws SQLException { //mark order as complete
        PreparedStatement stmt = conn.prepareStatement("update orders set active = false where owner = ?");
        stmt.setString(1 , orderOwner);

    }

}

