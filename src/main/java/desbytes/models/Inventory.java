package desbytes.models;


import org.springframework.jdbc.core.JdbcTemplate;
/**
 * A Grocery_Order object representing a Grocery_Order tuple
 */
public class Inventory {

    private String product_id;
    private int store_id;
    private int qty;
    private int aisle;


    public Grocery_Order(String product_id, int store_id, int qty, int aisle) {
        this.product_id = product_id;
        this.store_id = store_id;
        this.qty = qty;
        this.aisle = aisle;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getAisle() {
        return aisle;
    }

    public void setAisle(int aisle) {
        this.aisle = aisle;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "product_id='" + product_id + '\'' +
                ", store_id=" + store_id +
                ", qty=" + qty +
                ", aisle=" + aisle +
                '}';
    }
}