package com.example.BookStore.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public class Comment {
	private int id;
	private Date createDate;
	private String cmt;
	private Customer customer;
	private Product product;
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comment(int id,Date createDate, String cmt, Customer customer, Product product) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.cmt = cmt;
		this.customer = customer;
		this.product = product;
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
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", createDate=" + createDate + ", cmt=" + cmt + ", customer=" + customer
				+ ", product=" + product + "]";
	}
	
	
}
