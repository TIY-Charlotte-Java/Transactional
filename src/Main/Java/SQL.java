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
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("select ? from users.name");
        stmt.setString(1, testName);

        String name = result.getString("name");
        Integer order = result.getInt("order");
    }
    public static void

}

