package desbytes.models;

/**
 * An Order_Product object.
 */

public class Order_Product {

    private int order_id;
    private double product_id;
    private int qty;

    public Order_Product(int order_id, double product_id, int qty) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.qty = qty;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    @Override
    public String toString() {
        return "Order_Product{" +
                "order_id=" + order_id +
                ", product_id=" + product_id +
                ", qty=" + qty +
                '}';
    }
}
