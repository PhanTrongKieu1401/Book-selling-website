package com.example.BookStore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.BookStore.model.Category;

public class CategoryDAO {
	private String jdbcPk = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String jdbcUrl = "jdbc:sqlserver://localhost:1433; database = bookstore; user = sa; password = Kiki1401moon0208; encrypt = false";
	private static final String SELECT_CATEGORIES = "SELECT * FROM category";
	private static final String SELECT_CATEGORY = "SELECT * FROM category WHERE id=?";
	public CategoryDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(jdbcPk);
			connection = DriverManager.getConnection(jdbcUrl);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public List<Category> selectAllCategory() {
		List<Category> categories = new ArrayList<Category>();		
		try (
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORIES);	
		) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				categories.add(new Category(id, name));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return categories;
	}
	public Category selectCategory(String id) {
		try (
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY);
		){
			preparedStatement.setString(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				return new Category(resultSet.getInt("id"),
						resultSet.getString("name"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new Category();
	}
}
