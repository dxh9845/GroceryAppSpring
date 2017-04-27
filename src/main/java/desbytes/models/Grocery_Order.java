package desbytes.models;

import java.sql.Date;

/**
 * A Grocery_Order object representing a Grocery_Order tuple
 */
public class Grocery_Order {
    private int order_id;
    private Date order_time;
    private int store_id;
    private int user_id;


    public Grocery_Order(int order_id, Date order_time, int store_id, int user_id) {
        this.order_id = order_id;
        this.order_time = order_time;
        this.store_id = store_id;
        this.user_id = user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
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

    @Override
    public String toString() {
        return "Grocery_Order{" +
                "order_id=" + order_id +
                ", order_time='" + order_time.toString() + '\'' +
                ", store_id=" + store_id +
                ", user_id=" + user_id +
                '}';
    }
}
