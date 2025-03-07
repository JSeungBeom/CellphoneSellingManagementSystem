package app.cellphone.dto;

public class UserDto {
	private int userId;
	private String username;
	private String password;
	private String salt;
	
	public UserDto() {}
	
	public UserDto(int userId, String username, String password, String salt) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.salt = salt;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
