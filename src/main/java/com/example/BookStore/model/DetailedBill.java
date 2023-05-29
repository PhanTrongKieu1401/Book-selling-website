package com.example.BookStore.model;

import java.io.Serializable;
import java.util.Objects;


public class DetailedBill implements Serializable{
	private int id;
	private float price;
	private int quantity;
	private float amount;
	private Product product;
	public DetailedBill() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DetailedBill(int id, float price, int quantity, float amount, Product product) {
		super();
		this.id = id;
		this.price = price;
		this.quantity = quantity;
		this.amount = amount;
		this.product = product;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "DetailedBill [id=" + id + ", price=" + price + ", quantity=" + quantity + ", amount=" + amount
				+ ", product=" + product + "]";
	}
	
}
