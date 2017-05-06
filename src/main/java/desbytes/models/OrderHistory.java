package desbytes.models;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Jeff on 4/29/2017.
 */
public class OrderHistory {
    private HashMap<Product, Integer> productList = new HashMap<>();
    private Timestamp order_time;
    private int store_id;
    private int user_id;
    private int order_id;

    public OrderHistory(HashMap<Product, Integer> productList, Timestamp order_time, int store_id, int user_id, int order_id) {
        this.productList = productList;
        this.order_time = order_time;
        this.store_id = store_id;
        this.user_id = user_id;
        this.order_id = order_id;
    }

    public OrderHistory(){
    }

    public Set<Product> getProducts()
    {
        return productList.keySet();
    }

    public HashMap<Product, Integer> getProductList() {
        return productList;
    }

    public Timestamp getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Timestamp order_time) {
        this.order_time = order_time;
    }

    public void setProductList(HashMap<Product, Integer> productList) {
        this.productList = productList;
    }


    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
