package app.cellphone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Phone {
	
	@Id
	private int phone_id;
	
	private String brand;
	
	private String name;
	
	private int price;
	
	private int count;
	
	public Phone() {}

	public Phone(int phoneId, String brand, String name, int price, int count) {
		super();
		this.phone_id = phoneId;
		this.brand = brand;
		this.name = name;
		this.price = price;
		this.count = count;
	}

	public int getPhoneId() {
		return phone_id;
	}

	public void setPhoneId(int phoneId) {
		this.phone_id = phoneId;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
