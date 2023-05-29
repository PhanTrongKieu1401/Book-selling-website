package com.example.BookStore.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;


public class Bill implements Serializable{
	private int id;
	private Date createDate;
	private float totalAmount;
	private Customer customer;
	private String name;
	private String address;
	private String email;
	private String phone;
	private String note;
	private String status;
	private ArrayList<DetailedBill> detailedBills;
	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bill(int id, Date createDate, float totalAmount, Customer customer, String name, String address,
			String email, String phone, String note,String status, ArrayList<DetailedBill> detailedBills) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.totalAmount = totalAmount;
		this.customer = customer;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.note = note;
		this.status = status;
		this.detailedBills = detailedBills;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<DetailedBill> getDetailedBills() {
		return detailedBills;
	}
	public void setDetailedBills(ArrayList<DetailedBill> detailedBills) {
		this.detailedBills = detailedBills;
	}
	@Override
	public String toString() {
		return "Bill [id=" + id + ", createDate=" + createDate + ", totalAmount=" + totalAmount + ", customer="
				+ customer + ", name=" + name + ", address=" + address + ", email=" + email + ", phone=" + phone
				+ ", note=" + note + ", detailedBills=" + detailedBills + "]";
	}
	
	
}
