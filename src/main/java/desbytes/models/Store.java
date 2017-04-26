package desbytes.models;



/**
 * A store object representing the Store in the Database table
 */
public class Store
{
    private int store_id;
    private String name;
    private String location;

    public Store(int store_id, String name, String location) {
        this.store_id = store_id;
        this.name = name;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return String.format(
                "Store[StoreID=%d, Name='%s', Location='%s']",
                this.store_id, this.name, this.location);
    }


}
