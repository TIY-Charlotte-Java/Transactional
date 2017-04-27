/**
 * Created by Jake on 4/27/17.
 */
public class Order {
    String orderNumber;
    String itemName;
    Double itemCost;
    Double itemQuantity;
    Boolean activeOrder;

    public Order(String orderNumber, String itemName, Double itemCost, Double itemQuantity) {
        this.orderNumber = orderNumber;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemQuantity = itemQuantity;
    }
}


