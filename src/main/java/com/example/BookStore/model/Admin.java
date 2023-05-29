package com.example.BookStore.model;

public class Admin {
	private String username;
	private String password;
	private String email;
	private String userRole;
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Admin(String username, String password, String email, String userRole) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	@Override
	public String toString() {
		return "Admin [username=" + username + ", password=" + password + ", email=" + email + ", userRole=" + userRole
				+ "]";
	}
	
}
