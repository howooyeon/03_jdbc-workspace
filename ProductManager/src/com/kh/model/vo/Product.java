package com.kh.model.vo;

public class Product {
	private String product_Id;
	private String p_Name;
	private int price;
	private String description;
	private String stock;
	
	public Product() {}
	
	public Product(String productId, String pName, int price, String description, String stock) {
		super();
		this.product_Id = productId;
		this.p_Name = pName;
		this.price = price;
		this.description = description;
		this.stock = stock;
	}
	public String getProductId() {
		return product_Id;
	}
	public void setProductId(String productId) {
		this.product_Id = productId;
	}
	public String getpName() {
		return p_Name;
	}
	public void setpName(String pName) {
		this.p_Name = pName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Product [productId=" + product_Id + ", pName=" + p_Name + ", price=" + price + ", description="
				+ description + ", stock=" + stock + "]";
	}

}
