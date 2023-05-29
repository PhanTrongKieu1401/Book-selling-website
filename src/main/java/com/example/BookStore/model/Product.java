package com.example.BookStore.model;

import java.awt.Image;
import java.sql.Date;
import java.util.Objects;

public class Product {
	private int idPro;
	private String imagepPro;
	private String titlePro;
	private String authorPro;
	private String categoryPro;
	private int pageNumberPro;
	private int sellNumberPro;
	private Date createDatePro;
	private float pricePro;
	private String descriptPro;
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Product(int idPro, String imagepPro, String titlePro, String authorPro, String categoryPro,
			int pageNumberPro, int sellNumberPro, Date createDatePro, float pricePro, String descriptPro) {
		super();
		this.idPro = idPro;
		this.imagepPro = imagepPro;
		this.titlePro = titlePro;
		this.authorPro = authorPro;
		this.categoryPro = categoryPro;
		this.pageNumberPro = pageNumberPro;
		this.sellNumberPro = sellNumberPro;
		this.createDatePro = createDatePro;
		this.pricePro = pricePro;
		this.descriptPro = descriptPro;
	}

	public int getIdPro() {
		return idPro;
	}
	public void setIdPro(int idPro) {
		this.idPro = idPro;
	}
	public String getImagepPro() {
		return imagepPro;
	}
	public void setImagepPro(String imagepPro) {
		this.imagepPro = imagepPro;
	}
	public String getTitlePro() {
		return titlePro;
	}
	public void setTitlePro(String titlePro) {
		this.titlePro = titlePro;
	}
	public String getAuthorPro() {
		return authorPro;
	}
	public void setAuthorPro(String authorPro) {
		this.authorPro = authorPro;
	}
	public String getCategoryPro() {
		return categoryPro;
	}
	public void setCategoryPro(String categoryPro) {
		this.categoryPro = categoryPro;
	}
	public int getPageNumberPro() {
		return pageNumberPro;
	}
	public void setPageNumberPro(int pageNumberPro) {
		this.pageNumberPro = pageNumberPro;
	}
	public int getSellNumberPro() {
		return sellNumberPro;
	}
	public void setSellNumberPro(int sellNumberPro) {
		this.sellNumberPro = sellNumberPro;
	}
	public Date getCreateDatePro() {
		return createDatePro;
	}
	public void setCreateDatePro(Date createDayPro) {
		this.createDatePro = createDayPro;
	}
	public float getPricePro() {
		return pricePro;
	}
	public void setPricePro(float pricePro) {
		this.pricePro = pricePro;
	}
	public String getDescriptPro() {
		return descriptPro;
	}
	public void setDescriptPro(String descriptPro) {
		this.descriptPro = descriptPro;
	}
	public boolean equals(Object obj) {
		if (obj == this) {
	        return true;
	    }
		if (!(obj instanceof Product)) {
	        return false;
	    }
	    Product other = (Product) obj;
	    return Objects.equals(idPro, other.idPro)
	    		&& Objects.equals(titlePro, other.titlePro)
	            && Objects.equals(authorPro, other.authorPro)
	            && Objects.equals(pricePro, other.pricePro);
	}
	@Override
	public String toString() {
		return "Product [idPro=" + idPro + ", imagepPro=" + imagepPro + ", titlePro=" + titlePro + ", authorPro="
				+ authorPro + ", categoryPro=" + categoryPro + ", pageNumberPro=" + pageNumberPro + ", sellNumberPro="
				+ sellNumberPro + ", createDatePro=" + createDatePro + ", pricePro=" + pricePro + ", descriptPro="
				+ descriptPro + "]";
	}

	
}
