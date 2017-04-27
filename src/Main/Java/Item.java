/**
 * Created by Jake on 4/27/17.
 */
public class Item {
    String orderName;
    String itemName;
    Double itemCost;
    Double itemQuantity;




    public Item(String itemName, Double itemCost, Double itemQuantity) {
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemQuantity = itemQuantity;

    }

    public Item(String orderName, String itemName, Double itemCost, Double itemQuantity) {
        this.orderName = orderName;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemQuantity = itemQuantity;
    }
}
