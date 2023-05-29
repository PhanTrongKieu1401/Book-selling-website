package com.example.BookStore.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public class Evaluation {
	private int id;
	private float star;
	private Customer customer;
	private Product product;
	public Evaluation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Evaluation(int id, float star, Customer customer, Product product) {
		super();
		this.id = id;
		this.star = star;
		this.customer = customer;
		this.product = product;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getStar() {
		return star;
	}
	public void setStar(float star) {
		this.star = star;
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
		return "Evaluation [id=" + id + ", star=" + star + ", customer=" + customer + ", product=" + product + "]";
	}
	
}
