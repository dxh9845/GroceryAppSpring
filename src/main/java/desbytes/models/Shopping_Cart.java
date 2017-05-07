package desbytes.models;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.HashMap;
import java.util.Set;

/**
 * A shopping cart object.
 */

public class Shopping_Cart
{
    private Customer customer;
    private HashMap<Product, Integer> productList = new HashMap<>();
    private int customer_id;

    public Shopping_Cart(Customer customer, HashMap<Product, Integer> productList)
    {
        this.customer = customer;
        this.productList = productList;
    }
    public Shopping_Cart(){}

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public HashMap<Product, Integer> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<Product, Integer> productList) {
        this.productList = productList;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Set<Product> getProducts()
    {
        return productList.keySet();
    }
}
