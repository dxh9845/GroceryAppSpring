package desbytes.models;
import java.sql.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Jeff on 4/29/2017.
 */
public class OrderHistory {
    private HashMap<Product, Integer> productList = new HashMap<>();
    private Date order_time;
    private Store store;
    private int user_id;
    private int order_id;

    public OrderHistory(HashMap<Product, Integer> productList, Date order_time, Store store, int user_id, int order_id) {
        this.productList = productList;
        this.order_time = order_time;
        this.store = store;
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

    public void setProductList(HashMap<Product, Integer> productList) {
        this.productList = productList;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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
