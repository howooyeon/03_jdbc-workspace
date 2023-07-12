package com.kh.model.vo;

public class Phone {
	private String userName;
	private int age;
	private String address;
	private String phone;
	
	public Phone() { }
	
	public Phone(String userName, int age, String address, String phone) {
		super();
		this.userName = userName;
		this.age = age;
		this.address = address;
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Phone [userName=" + userName + ", age=" + age + ", address=" + address + ", phone=" + phone + "]";
	}
	
	
	
}
