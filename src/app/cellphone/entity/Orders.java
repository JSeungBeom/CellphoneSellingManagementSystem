package app.cellphone.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Orders {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int order_id;
	
	private int user_id;
	
	private int phone_id;
	
	private int saleprice;
	
	private int ordercount;
	
	private Timestamp orderdate;
	
	public Orders() {};

	public Orders(int orderId, int userId, int phoneId, int saleprice, int ordercount, Timestamp orderdate) {
		super();
		this.order_id = orderId;
		this.user_id = userId;
		this.phone_id = phoneId;
		this.saleprice = saleprice;
		this.ordercount = ordercount;
		this.orderdate = orderdate;
	}

	public int getOrderId() {
		return order_id;
	}

	public void setOrderId(int orderId) {
		this.order_id = orderId;
	}

	public int getUserId() {
		return user_id;
	}

	public void setUserId(int userId) {
		this.user_id = userId;
	}

	public int getPhoneId() {
		return phone_id;
	}

	public void setPhoneId(int phoneId) {
		this.phone_id = phoneId;
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
