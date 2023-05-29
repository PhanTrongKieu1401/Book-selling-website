package com.example.BookStore.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.BookStore.model.Bill;
import com.example.BookStore.model.Customer;
import com.example.BookStore.model.DetailedBill;
import com.example.BookStore.model.Product;

public class DetailedBillDAO extends DAO{
	private ProductDAO productDAO = new ProductDAO();
	
	private static final String SELECT_ALL_DETAILEDBILL_BY_BILL = "SELECT * FROM detailedbill WHERE billID = ?";
	private static final String DELETE_ALL_DETAILEDBILL = "DELETE FROM detailedbill WHERE billID = ?";
	
	public DetailedBillDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<DetailedBill> selectDetaiBills(int billID) throws ClassNotFoundException {
		ArrayList<DetailedBill> detailedBills = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DETAILEDBILL_BY_BILL);
			preparedStatement.setInt(1, billID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				float price = resultSet.getFloat("price");
				int quantity = resultSet.getInt("quantity");
				float amount = (float) (price * quantity);
				Product product = productDAO.selectProduct(resultSet.getString("productID"));
				detailedBills.add(new DetailedBill(id, price, quantity, amount, product));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return detailedBills;
	}
	public boolean deleteDetailedBill(String billID){
		boolean value = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_DETAILEDBILL);
			preparedStatement.setString(1, billID);
			preparedStatement.executeUpdate();
			value = true;
			preparedStatement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
}
