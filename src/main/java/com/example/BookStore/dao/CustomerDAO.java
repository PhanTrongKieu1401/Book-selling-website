package com.example.BookStore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.BookStore.model.Admin;
import com.example.BookStore.model.Customer;

public class CustomerDAO {
	private String jdbcPk = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String jdbcUrl = "jdbc:sqlserver://localhost:1433; database = bookstore; user = sa; password = Kiki1401moon0208; encrypt = false";
	private final static String SELECT_CUSTOMER = "SELECT * FROM customerac WHERE username = ?";
	private final static String CHECK_LOGIN = "SELECT * FROM customerac WHERE username = ? AND password = ?";
	private final static String CHECK_EXISTS = "SELECT * FROM customerac WHERE EXISTS (SELECT * FROM customerac WHERE username = ?)";
	private final static String ADD_CUSTOMER = "INSERT INTO customerac(username,password,userRole) VALUES (?, ?, ?)";
	private final static String UPDATE_CUSTOMER = "UPDATE customerac SET name = ?, email = ?, address = ?, phone = ? WHERE username = ?";
	
	public CustomerDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Connection getConnection() {
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
	
	public Customer takeCustomer(Customer customer) throws ClassNotFoundException {
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER);
		) {
			preparedStatement.setString(1, customer.getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				String username = rs.getString(1);
				String password = rs.getString(2);
				String name = rs.getString(3);
				String email = rs.getString(4);
				String address = rs.getString(5);
				String phone = rs.getString(6);
				String role = rs.getString(7);
				return new Customer(username, password, name, email, address, phone, role);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean checkLogin(Customer customer) throws ClassNotFoundException {
		boolean value = false;
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LOGIN);
		) {
			preparedStatement.setString(1, customer.getUsername());
			preparedStatement.setString(2, customer.getPassword());
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				value = true;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
	
	public boolean checkExist(Customer customer) {
		boolean value = true;
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EXISTS);
		) {
			preparedStatement.setString(1, customer.getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				value = false;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
	
	public boolean register(Customer customer) {
		boolean value = false;
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(ADD_CUSTOMER);
		) {
			preparedStatement.setString(1, customer.getUsername());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.setString(3, "customer");
			int resultSet = preparedStatement.executeUpdate();
			value = true;
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
	
	public boolean update(Customer customer) {
		boolean value = false;
		try(
				Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER);
			) {
				preparedStatement.setString(1, customer.getName());
				preparedStatement.setString(2, customer.getEmail());
				preparedStatement.setString(3, customer.getAddress());
				preparedStatement.setString(4, customer.getPhone());
				preparedStatement.setString(5, customer.getUsername());
				int rs = preparedStatement.executeUpdate();
				value = true;
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		return value;
	}
}
