package com.example.BookStore.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.BookStore.edu.FileUploadUtil;
import com.example.BookStore.model.Category;
import com.example.BookStore.model.Customer;
import com.example.BookStore.model.Product;


public class ProductDAO {
	private String jdbcPk = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String jdbcUrl = "jdbc:sqlserver://localhost:1433; database = bookstore; user = sa; password = Kiki1401moon0208; encrypt = false";
	private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM product";
	private static final String FIND_ALL_PRODUCTS_BY_TITLE = "SELECT * FROM product WHERE title LIKE ?";
	private static final String FIND_ALL_PRODUCTS_BY_AUTHOR = "SELECT * FROM product WHERE author LIKE ?";
	private static final String FIND_ALL_PRODUCTS_BY_CATEGORY = "SELECT * FROM product WHERE category LIKE ?";
	private static final String FIND_ALL_PRODUCTS_BY_TITLE_OR_AUTHOR = "SELECT * FROM product WHERE title LIKE ? OR author LIKE ?";
	private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product WHERE productCode = ?";
	private static final String INSERT_PRODUCT_SQL = "INSERT INTO product(imagep,title,author,category,pageNumber,createDate,price,descript) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_PRODUCT_SQL = "UPDATE product SET imagep = ?, title = ?, author = ?, category = ?, pageNumber = ?, createDate = ?, descript = ? WHERE productCode = ?";
	private static final String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE productCode = ?";
	private static final String CHECK_EXISTS = "SELECT * FROM product WHERE title = ? AND author = ?";
	
	private CategoryDAO categoryDAO = new CategoryDAO();
	
	public String findCategory(int i) {
		List<Category> categories = categoryDAO.selectAllCategory();
		for(Category category:categories) {
			if(category.getId() == i) {
				return category.getName();
			}
		}	
		return null;
	}
	
	public boolean checkCategory(String s) {
		boolean check = true;
		try {
			int value = Integer.parseInt(s);
		} catch (Exception e) {
			// TODO: handle exception
			check = false;
		}
		return check;
	}
	
	public ProductDAO() {}
	protected Connection getConnection() throws ClassNotFoundException {
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
	//chọn list sách
	public List<Product> selectAllProducts() throws ClassNotFoundException{
		List<Product> products = new ArrayList<Product>();
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
		) {
			ResultSet resultSet =preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("productCode");
				String imagep = resultSet.getString("imagep");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String category = null;
				if(checkCategory(resultSet.getString("category"))) {
					category = findCategory(Integer.valueOf(resultSet.getString("category")));
				}
				else {
					category = resultSet.getString("category");
				}
				int pageNumber = resultSet.getInt("pageNumber");
				int sellNumber = resultSet.getInt("sellNumber");
				Date createDate = resultSet.getDate("createDate");
				float price = resultSet.getFloat("price");
				String descript = resultSet.getString("descript");
				products.add(new Product(id,imagep,title,author,category,pageNumber,sellNumber,createDate,price,descript));	
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return products;
	}
	//Tìm kiếm sách theo title
	public List<Product> searchProductByTitle(String key) throws ClassNotFoundException {
		List<Product> products = new ArrayList<Product>();
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCTS_BY_TITLE);
		) {
			preparedStatement.setString(1,"%" + key + "%");
			ResultSet resultSet =preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("productCode");
				String imagep = resultSet.getString("imagep");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String category = null;
				if(checkCategory(resultSet.getString("category"))) {
					category = findCategory(Integer.valueOf(resultSet.getString("category")));
				}
				else {
					category = resultSet.getString("category");
				}
				int pageNumber = resultSet.getInt("pageNumber");
				int sellNumber = resultSet.getInt("sellNumber");
				Date createDate = resultSet.getDate("createDate");
				float price = resultSet.getFloat("price");
				String descript = resultSet.getString("descript");
				
				products.add(new Product(id,imagep,title,author,category,pageNumber,sellNumber,createDate,price,descript));	
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return products;
	}
	//Tìm kiếm sách theo author
	public List<Product> searchProductByAuthor(String key) throws ClassNotFoundException {
		List<Product> products = new ArrayList<Product>();
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCTS_BY_AUTHOR);
		) {
			preparedStatement.setString(1,"%" + key + "%");
			ResultSet resultSet =preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("productCode");
				String imagep = resultSet.getString("imagep");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String category = null;
				if(checkCategory(resultSet.getString("category"))) {
					category = findCategory(Integer.valueOf(resultSet.getString("category")));
				}
				else {
					category = resultSet.getString("category");
				}
				int pageNumber = resultSet.getInt("pageNumber");
				int sellNumber = resultSet.getInt("sellNumber");
				Date createDate = resultSet.getDate("createDate");
				float price = resultSet.getFloat("price");
				String descript = resultSet.getString("descript");
				
				products.add(new Product(id,imagep,title,author,category,pageNumber,sellNumber,createDate,price,descript));	
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return products;
	}
	//Tìm kiếm sách theo category
	public List<Product> searchProductByCategory(String key) throws ClassNotFoundException {
		List<Product> products = new ArrayList<Product>();
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCTS_BY_CATEGORY);
		) {
			preparedStatement.setString(1,"%" + key + "%");
			ResultSet resultSet =preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("productCode");
				String imagep = resultSet.getString("imagep");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String category = null;
				if(checkCategory(resultSet.getString("category"))) {
					category = findCategory(Integer.valueOf(resultSet.getString("category")));
				}
				else {
					category = resultSet.getString("category");
				}
				int pageNumber = resultSet.getInt("pageNumber");
				int sellNumber = resultSet.getInt("sellNumber");
				Date createDate = resultSet.getDate("createDate");
				float price = resultSet.getFloat("price");
				String descript = resultSet.getString("descript");
				
				products.add(new Product(id,imagep,title,author,category,pageNumber,sellNumber,createDate,price,descript));	
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return products;
	}
	//Tìm kiếm sách theo title or author
	public List<Product> searchProductByTitleOrAuthor(String key) throws ClassNotFoundException {
		List<Product> products = new ArrayList<Product>();
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCTS_BY_TITLE_OR_AUTHOR);
		) {
			preparedStatement.setString(1,"%" + key + "%");
			preparedStatement.setString(2,"%" + key + "%");
			ResultSet resultSet =preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("productCode");
				String imagep = resultSet.getString("imagep");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String category = null;
				if(checkCategory(resultSet.getString("category"))) {
					category = findCategory(Integer.valueOf(resultSet.getString("category")));
				}
				else {
					category = resultSet.getString("category");
				}
				int pageNumber = resultSet.getInt("pageNumber");
				int sellNumber = resultSet.getInt("sellNumber");
				Date createDate = resultSet.getDate("createDate");
				float price = resultSet.getFloat("price");
				String descript = resultSet.getString("descript");
				
				products.add(new Product(id,imagep,title,author,category,pageNumber,sellNumber,createDate,price,descript));	
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return products;
	}
	//chọn một quyển
	public Product selectProduct(String productCode) throws ClassNotFoundException{
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
		){		 
			preparedStatement.setString(1, productCode);
			ResultSet resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	String category = null;
	        	if(checkCategory(resultSet.getString("category"))) {
					category = findCategory(Integer.valueOf(resultSet.getString("category")));
				}
				else {
					category = resultSet.getString("category");
				}
	        	return new Product(resultSet.getInt("productCode"),
	        	resultSet.getString("imagep"),
	        	resultSet.getString("title"),
				resultSet.getString("author"),
				category,
				resultSet.getInt("pageNumber"),
				resultSet.getInt("sellNumber"),
				resultSet.getDate("createDate"),
				resultSet.getFloat("price"),
				resultSet.getString("descript")
				);
	        }		
		} catch (SQLException e) {
			 // TODO: handle exception
			e.printStackTrace();
		}
		return new Product();
	}
	//thêm sách mới
	public boolean insertProduct(Product product) throws ClassNotFoundException, IOException{
		boolean value = false;
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);
		){  
			preparedStatement.setString(1, product.getImagepPro());
			preparedStatement.setString(2, product.getTitlePro());
			preparedStatement.setString(3, product.getAuthorPro());
			preparedStatement.setString(4, product.getCategoryPro());
			preparedStatement.setInt(5, product.getPageNumberPro());
			preparedStatement.setDate(6, product.getCreateDatePro());
			preparedStatement.setFloat(7, product.getPricePro());
			preparedStatement.setString(8, product.getDescriptPro());
			preparedStatement.executeUpdate();
			value = true;
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
	//cập nhật sách
	public boolean updateProduct(Product product) throws ClassNotFoundException{
		boolean value = false;
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL);
		){		
			preparedStatement.setString(1, product.getImagepPro());
			preparedStatement.setString(2, product.getTitlePro());
			preparedStatement.setString(3, product.getAuthorPro());
			preparedStatement.setString(4, product.getCategoryPro());
			preparedStatement.setInt(5, product.getPageNumberPro());
			preparedStatement.setDate(6, product.getCreateDatePro());
			preparedStatement.setString(7, product.getDescriptPro());
			preparedStatement.setInt(8, product.getIdPro());
			preparedStatement.executeUpdate();
			value = true;
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
	//xóa
	public boolean deleteProduct(String productCode){
		boolean value = false;
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL);
		){	
			preparedStatement.setString(1, productCode);
			preparedStatement.executeUpdate();
			value = true;
			preparedStatement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
	//kiểm tra tồn tại
	public boolean checkExist(Product product) throws ClassNotFoundException {
		boolean value = true;
		try(
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EXISTS);
		) {
			preparedStatement.setString(1, product.getTitlePro());
			preparedStatement.setString(2, product.getAuthorPro());
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
}

