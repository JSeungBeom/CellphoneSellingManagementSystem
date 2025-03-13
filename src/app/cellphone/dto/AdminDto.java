package app.cellphone.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class AdminDto {
	
	private int adminId;
	private String username;
	private String password;
	
	public AdminDto() {}

	public AdminDto(int adminId, String username, String password) {
		super();
		this.adminId = adminId;
		this.username = username;
		this.password = password;
	}

	public int getUserId() {
		return adminId;
	}

	public void setUserId(int userId) {
		this.adminId = userId;
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
	
}
