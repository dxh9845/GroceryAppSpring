package desbytes.models;


import org.springframework.jdbc.core.JdbcTemplate;

/**
 * An App_User object representing an App_User tuple
 */
public class App_User
{
    private int id;
	private String username;
	private String name;
	private String password;
	private String phone;
	private String address;
	private int roll_id;

    public App_User(int id, String username, String name, String password, String phone,
		String address, int roll_id) {
        this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.roll_id = roll_id;
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

	public int getRoll_id() {
		return roll_id;
	}

	public void setRoll_id(int roll_id) {
		this.roll_id = roll_id;
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
				", roll_id=" + roll_id +
				'}';
	}
}
