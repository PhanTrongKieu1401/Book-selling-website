package com.example.BookStore.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.BookStore.model.Comment;
import com.example.BookStore.model.Customer;
import com.example.BookStore.model.Product;

public class CommentDAO extends DAO{
	private final static String SELECT_ALL_COMMENTS = "SELECT * FROM comment WHERE productID = ?";
	private final static String INSERT_COMMENT = "INSERT INTO comment(createDate, cmt, customerID, productID) VALUES (?, ?, ?, ?)";
	public CommentDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<Comment> selectAllCommentsOfBook(String productCode) throws ClassNotFoundException{
		List<Comment> comments = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_COMMENTS);
			ps.setString(1, productCode);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				Date createDate = rs.getDate("createDate");
				String cmt = rs.getString("cmt");
				CustomerDAO customerDAO = new CustomerDAO();
				Customer customerTMP = new Customer();
				customerTMP.setUsername(rs.getString("customerID"));
				Customer customer = customerDAO.takeCustomer(customerTMP);
				Product product = new Product();
				product.setIdPro(rs.getInt("productID"));
				comments.add(new Comment(id,createDate,cmt,customer,product));
			}
		} catch (SQLException e) {
			 // TODO: handle exception
			e.printStackTrace();
		}	
		return comments;
	}
	public boolean insertComment(Comment comment) throws ClassNotFoundException, IOException{
		boolean value = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMENT); 
			preparedStatement.setDate(1, comment.getCreateDate());
			preparedStatement.setString(2, comment.getCmt());
			preparedStatement.setString(3, comment.getCustomer().getUsername());
			preparedStatement.setInt(4, comment.getProduct().getIdPro());
			preparedStatement.executeUpdate();
			value = true;
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
}
