package com.example.BookStore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.javapoet.ClassName;

import com.example.BookStore.model.Admin;

public class AdminDAO {
	private String jdbcPk = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String jdbcUrl = "jdbc:sqlserver://localhost:1433; database = bookstore; user = sa; password = Kiki1401moon0208; encrypt = false";
	private final static String SELECT_ADMIN = "SELECT * FROM adminac WHERE username = ? AND password = ?";
	
	
	public AdminDAO() {
		super();
	}

	public Connection getConnection() throws ClassNotFoundException{
		Connection connection = null;
		try {
			Class.forName(jdbcPk);
			connection = DriverManager.getConnection(jdbcUrl);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return connection;
	}
	
	public boolean checkLogin(Admin admin) throws ClassNotFoundException {
		boolean value = false;
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ADMIN);
		) {
			preparedStatement.setString(1, admin.getUsername());
			preparedStatement.setString(2, admin.getPassword());
//			System.out.println(admin.getUsername() +" "+ admin.getPassword());
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				value = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
}
