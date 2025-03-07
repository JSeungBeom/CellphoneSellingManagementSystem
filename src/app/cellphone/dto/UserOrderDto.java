package app.cellphone.dto;

import java.sql.Timestamp;

public class UserOrderDto {
	private int orderId;
	private String brand;
	private String name;
	private int saleprice;
	private int ordercount;
	private Timestamp orderdate;
	
	public UserOrderDto() {}

	public UserOrderDto(int orderId, String brand, String name, int saleprice, int ordercount, Timestamp orderdate) {
		super();
		this.orderId = orderId;
		this.brand = brand;
		this.name = name;
		this.saleprice = saleprice;
		this.ordercount = ordercount;
		this.orderdate = orderdate;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(int saleprice) {
		this.saleprice = saleprice;
	}

	public int getOrdercount() {
		return ordercount;
	}

	public void setOrdercount(int ordercount) {
		this.ordercount = ordercount;
	}

	public Timestamp getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Timestamp orderdate) {
		this.orderdate = orderdate;
	}
	
}
