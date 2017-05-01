package desbytes.models;
import java.sql.Date;
import java.util.HashMap;

/**
 * Created by Jeff on 4/29/2017.
 */
public class OrderHistory {
    private HashMap<Product, Integer> productList = new HashMap<>();
    private Date date;
    private Store store;

    public OrderHistory(HashMap<Product, Integer> productList, Date date, Store store) {
        this.productList = productList;
        this.date = date;
        this.store = store;
    }

    public OrderHistory(){
    }

    public HashMap<Product, Integer> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<Product, Integer> productList) {
        this.productList = productList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
