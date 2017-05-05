package desbytes.models;



/**
 * Customer object representing a tuple in the Customer table
 */
public class Customer{
    private int user_id;
    private int pref_store_id;

    public Customer(int user_id, int pref_store_id) {
        this.user_id = user_id;
        this.pref_store_id = pref_store_id;
    }

    public Customer(){}

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPref_store_id() {
        return pref_store_id;
    }

    public void setPref_store_id(int pref_store_id) {
        this.pref_store_id = pref_store_id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "user_id=" + user_id +
                ", pref_store_id=" + pref_store_id +
                '}';
    }
}
