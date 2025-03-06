package app.cellphone.dto;

import java.sql.Timestamp;

public class OrderDto {
	private int orderId;
	private int userId;
	private int phoneId;
	private int saleprice;
	private int ordercount;
	private Timestamp orderdate;
	
	public OrderDto() {}
	
	public OrderDto(int orderId, int userId, int phoneId, int saleprice, int ordercount, Timestamp orderdate) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.phoneId = phoneId;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
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

	public void setOrderdate(Timestamp orderDate) {
		this.orderdate = orderDate;
	}
	
}
