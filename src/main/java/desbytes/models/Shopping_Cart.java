package desbytes.models;
import java.util.HashMap;
import java.util.Set;

/**
 * A shopping cart object.
 */

public class Shopping_Cart
{
    private Customer customer;
    private HashMap<Product, Integer> productList = new HashMap<>();

    public Shopping_Cart(Customer customer, HashMap<Product, Integer> productList)
    {
        this.customer = customer;
        this.productList = productList;
    }

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

    public Set<Product> getProducts()
    {
        return productList.keySet();
    }
}
