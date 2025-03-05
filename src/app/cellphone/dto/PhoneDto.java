package app.cellphone.dto;

public class PhoneDto {
	private int phoneId;
	private String brand;
	private String name;
	private int price;
	private int count;
	
	public PhoneDto() {}
	
	public PhoneDto(int phoneId, String brand, String name, int price, int count) {
		super();
		this.phoneId = phoneId;
		this.brand = brand;
		this.name = name;
		this.price = price;
		this.count = count;
	}
	
	public int getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
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
