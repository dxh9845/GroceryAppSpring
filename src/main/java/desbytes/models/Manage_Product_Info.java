package desbytes.models;

/**
 * Created by zach on 4/27/17.
 */
public class Manage_Product_Info {
    private double product_id;
    private double store_id;
    private String name;
    private float price;
    private int qty;
    private int aisle;

    public Manage_Product_Info(){
        super();
    }
    public Manage_Product_Info(double product_id, double store_id, String name, float price, int qty, int aisle) {
        this.product_id = product_id;
        this.store_id = store_id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.aisle = aisle;
    }

    public double getProduct_id() {
        return product_id;
    }

    public void setProduct_id(double product_id) {
        this.product_id = product_id;
    }

    public double getStore_id() {
        return store_id;
    }

    public void setStore_id(double store_id) {
        this.store_id = store_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
        return "Manage_Product_Info{" +
                "product_id=" + product_id +
                ", store_id=" + store_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", aisle=" + aisle +
                '}';
    }
}
