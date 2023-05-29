package com.example.BookStore.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.BookStore.model.Bill;
import com.example.BookStore.model.Customer;
import com.example.BookStore.model.DetailedBill;
import com.example.BookStore.model.Product;


public class BillDAO extends DAO{
	private DetailedBillDAO detailedBillDAO = new DetailedBillDAO();
	
	private static final String SELECT_ALL_BILL_BY_CUSTOMER = "SELECT * FROM bill WHERE customerID = ?";
	private static final String SELECT_BILL = "SELECT * FROM bill WHERE id = ?";
	private static final String ADD_BILL = "INSERT INTO bill(createDate, customerID, name, address, email, phone, note, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String ADD_DETAILED_BILL = "INSERT INTO detailedbill(price, quantity, billID, productID) VALUES(?,?,?,?)";
	private static final String DELETE_BILL = "DELETE FROM bill WHERE id = ?";
	
	public BillDAO() {
		super();
	}
	
	public List<Bill> selectBills(Customer customer) throws ClassNotFoundException {
		List<Bill> bills = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BILL_BY_CUSTOMER);
			preparedStatement.setString(1, customer.getUsername());
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				ArrayList<DetailedBill> detailedBills = detailedBillDAO.selectDetaiBills(id);
				float totalAmount = (float)Math.floor(detailedBills.stream().mapToDouble(DetailedBill::getAmount).sum() * 1.08);
				Date createDate = resultSet.getDate("createDate");
				String name = resultSet.getString("name");
				String address = resultSet.getString("address");
				String email = resultSet.getString("email");
				String phone = resultSet.getString("phone");
				String note = resultSet.getString("note");
				String status = resultSet.getString("status");
				
				bills.add(new Bill(id, createDate, totalAmount, customer, name, address, email, phone, note, status, detailedBills));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bills;
	}
	public Bill selectBill(int billID) throws ClassNotFoundException{
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BILL);
			preparedStatement.setInt(1, billID);
			ResultSet resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	Customer customer = new Customer();
	        	customer.setUsername(resultSet.getString(3));
	        	ArrayList<DetailedBill> detailedBills = detailedBillDAO.selectDetaiBills(billID);
	        	float totalAmount = (float)Math.floor(detailedBills.stream().mapToDouble(DetailedBill::getAmount).sum() * 1.08);
	        	return new Bill(
	        			resultSet.getInt(1),
	        			resultSet.getDate(2),
	        			totalAmount,
	        			customer,
	        			resultSet.getString(4),
	        			resultSet.getString(5),
	        			resultSet.getString(6),
	        			resultSet.getString(7),
	        			resultSet.getString(8),
	        			resultSet.getString(9),
	        			detailedBills
	        			);
	        }		
		} catch (SQLException e) {
			 // TODO: handle exception
			e.printStackTrace();
		}
		return new Bill();
	}
	public boolean addBill(Bill b) throws ClassNotFoundException, SQLException {
		boolean result = true;
		try {
			connection.setAutoCommit(false);
			PreparedStatement ps = connection.prepareStatement(ADD_BILL, Statement.RETURN_GENERATED_KEYS);
			ps.setDate(1, b.getCreateDate());
			ps.setString(2, b.getCustomer().getUsername());
			ps.setString(3, b.getName());
			ps.setString(4, b.getAddress());
			ps.setString(5, b.getEmail());
			ps.setString(6, b.getPhone());
			ps.setString(7, b.getNote());
			ps.setString(8, "Chờ xét duyệt");
		
			ps.executeUpdate();	
			//lấy id của hóa đơn mới thêm
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				b.setId(generatedKeys.getInt(1));
			
				//chèn danh sách detailedInvoice
				for(DetailedBill db: b.getDetailedBills()) {
					//insert detailed invoice
					ps = connection.prepareStatement(ADD_DETAILED_BILL, Statement.RETURN_GENERATED_KEYS);
					ps.setFloat(1, db.getPrice());
					ps.setInt(2, db.getQuantity());
					ps.setInt(3, b.getId());
					ps.setInt(4, db.getProduct().getIdPro());
				
					ps.executeUpdate();	
					//lấy id của hóa đơn mới thêm
					generatedKeys = ps.getGeneratedKeys();
					if (generatedKeys.next()) {
						db.setId(generatedKeys.getInt(1));
					}
				}
			}
		}catch(Exception e) {
			result = false;			
			try {				
				connection.rollback();
			}catch(Exception ex) {
				result = false;
				ex.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {		
				connection.setAutoCommit(true);
			}catch(Exception ex) {
				result = false;
				ex.printStackTrace();
			}
		}
		return result;
	}
	public boolean deleteBill(String billID){
		boolean value = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BILL);
			preparedStatement.setString(1, billID);
			detailedBillDAO.deleteDetailedBill(billID);
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
