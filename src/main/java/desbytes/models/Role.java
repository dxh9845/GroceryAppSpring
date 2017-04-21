package desbytes.models;

/**
 * Role object representing a tuple from the Role table
 */
public class Role {
    private int role_id;
    private String role_desc;

    public Role(int role_id, String role_desc) {
        this.role_id = role_id;
        this.role_desc = role_desc;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_desc() {
        return role_desc;
    }

    public void setRole_desc(String role_desc) {
        this.role_desc = role_desc;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + role_id +
                ", role_desc='" + role_desc + '\'' +
                '}';
    }
}
