/**
 * Created by Jake on 4/27/17.
 */
public class Order {
    String owner;
    Integer orderID;
    Boolean activeOrder;


    public Order(String owner, Integer orderID, Boolean activeOrder) {
        this.owner = owner;
        this.orderID = orderID;
        this.activeOrder = activeOrder;
    }
}


