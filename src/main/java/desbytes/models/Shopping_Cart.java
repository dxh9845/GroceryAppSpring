package desbytes.models;

/**
 * A shopping cart object.
 */

public class Shopping_Cart
{

    private int customer_id;
    private double product_id;
    private int qty;

    public Shopping_Cart(int customer_id, double product_id, int qty) {
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.qty = qty;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public double getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
