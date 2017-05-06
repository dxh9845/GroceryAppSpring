package desbytes.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by zach on 4/27/17.
 */
public class ProductInfo {
    @NotNull
    private String product_id;
    @NotNull
    private int store_id;
    @Size(min=1)
    private String name;
    @NotNull
    private float price;
    @NotNull
    private int qty;
    @NotNull
    private int aisle;

    public ProductInfo(){
        super();
    }
    public ProductInfo(String product_id, int store_id, String name, float price, int qty, int aisle) {
        this.product_id = product_id;
        this.store_id = store_id;
        this.name = name;
        this.price = price;
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
        return "ProductInfo{" +
                "product_id=" + product_id +
                ", store_id=" + store_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", aisle=" + aisle +
                '}';
    }
}
