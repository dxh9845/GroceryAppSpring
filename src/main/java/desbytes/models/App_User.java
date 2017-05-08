package desbytes.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * An App_User object representing an App_User tuple
 */

public class App_User
{
    private int id;
    @NotNull
    @Size(min=6, max=25)
	private String username;
    @NotNull
	@Size(max = 25, message = "Name must not exceed 25 characters.")
	private String name;
    @NotNull
	@Size(min=6, max=16)
	private String password;
    @NotNull
	@Size(min=10, max=10, message = "Phone numbers must be 10 characters long. Please remove any non-numeric characters.")
	private String phone;
    @NotNull
	@Size(max = 100, message = "The address must not exceed 100 characters.")
	private String address;
	private int role_id;

    public App_User(int id, String username, String name, String password, String phone,
		String address, int role_id) {
        this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.role_id = role_id;
    }

	public App_User() {
    	super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	@Override
	public String toString() {
		return "App_User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", role_id=" + role_id +
				'}';
	}
}
