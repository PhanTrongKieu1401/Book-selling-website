package com.example.BookStore.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.BookStore.model.Comment;
import com.example.BookStore.model.Customer;
import com.example.BookStore.model.Evaluation;
import com.example.BookStore.model.Product;

public class EvaluationDAO extends DAO{
	private final static String SELECT_ALL_EVALUATION = "SELECT * FROM evaluation WHERE productID = ?";
	private final static String INSERT_EVALUATION = "INSERT INTO evaluation(star, customerID, productID) VALUES (?, ?, ?)";
	public EvaluationDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<Evaluation> selectAllEvaluationsOfBook(String productCode){
		List<Evaluation> evaluations = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_EVALUATION);
			ps.setString(1, productCode);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				float star = rs.getFloat("star");
				Customer customer = new Customer();
				customer.setUsername(rs.getString("customerID"));
				Product product = new Product();
				product.setIdPro(rs.getInt("productID"));
				evaluations.add(new Evaluation(id,star,customer,product));
			}
		} catch (SQLException e) {
			 // TODO: handle exception
			e.printStackTrace();
		}	
		return evaluations;
	}
	public boolean insertEvaluation(Evaluation evaluation) throws ClassNotFoundException, IOException{
		boolean value = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVALUATION); 
			preparedStatement.setFloat(1, evaluation.getStar());
			preparedStatement.setString(2, evaluation.getCustomer().getUsername());
			preparedStatement.setInt(3, evaluation.getProduct().getIdPro());
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
