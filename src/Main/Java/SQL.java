import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Jake on 4/27/17.
 */
public class SQL {

//
//    public static ArrayList viewUsers(Connection conn) throws SQLException {
//        ArrayList<User> users = new ArrayList<>();
//        Statement stmt = conn.createStatement();
//        ResultSet results = stmt.executeQuery("select * from users");
//        while (results.next()) {
//            Integer id = results.getInt("id");
//            String name = results.getString("username");
//            users.add(new User(id, name));
//        }
//        return users;
//    }

    public static void createUser(Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("insert into users values (null, ?)");
        stmt.setString(1, username);
        stmt.execute();
    }

    public static void addItems(Connection conn, Item items) throws SQLException { //adds items to order
        PreparedStatement stmt = conn.prepareStatement("insert into items values(null,?,?,?,?)");
        stmt.setString(1, items.itemName);
        stmt.setDouble(2, items.itemCost);
        stmt.setDouble(3, items.itemQuantity);
        stmt.setInt(4, items.orderID);
        stmt.execute();
    }

    public static User selectUser(Connection conn, String testName) throws SQLException { //checks user vs database
        PreparedStatement stmt = conn.prepareStatement("select * from users where username = ?");
        stmt.setString(1, testName);
        ResultSet results = stmt.executeQuery();

        User u = null;

        if (results.next()) {
            String username = results.getString("username");
            int id = results.getInt("id");
            u = new User(id, username);
        }
        return u;
    }

    public static ArrayList total(Connection conn, String user) throws SQLException { //get items of order
        ArrayList all = new ArrayList();
        PreparedStatement stmt = conn.prepareStatement("select * from items inner join orders on orders.id = items.order_id where"
                + " orders.active_order = true and owner = ?");
        stmt.setString(1, user);
        ResultSet results = stmt.executeQuery();

        while (results.next()) {
            String name = results.getString("item_name");
            String cost = results.getString("item_cost");
            String quantity = results.getString("item_quantity");
            all.add(new Item(name, Double.valueOf(cost), Double.valueOf(quantity)));
        }
        return all;
    }

    public static void completeOrder(Connection conn, String orderOwner) throws SQLException { //mark order as complete
        PreparedStatement stmt = conn.prepareStatement("update orders set active_order = false where owner = ?");
        stmt.setString(1, orderOwner);
        stmt.execute();
    }

    public static Integer checkCart(Connection conn, String user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select orders.id from orders inner join users on orders.owner" +
                " = users.username where orders.active_order = true and orders.owner = ?");
        stmt.setString(1, user);
        ResultSet result = stmt.executeQuery();

        Integer ID = null;

        if (result.next()) {
            ID = result.getInt("id");
        } else {
            PreparedStatement stmt2 = conn.prepareStatement("insert into orders values(null, ?, ?)");
            stmt2.setString(1, user);
            stmt2.setBoolean(2, true);
            stmt2.execute();

            stmt2 = conn.prepareStatement("select id from orders where active_order = true");
            ResultSet results = stmt2.executeQuery();

            results.next();
            ID = results.getInt("id");

        }

        return ID;
    }
}

