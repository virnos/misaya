package com.misaya.pojo;

import java.util.Date;

public class Customer {

	/**
	 * 客户ID
	 */
	private String id;
	
	/**
	 * 客户姓名
	 */
	private String name;
	
	/**
	 * 性别
	 */
	private int gender;
	
	/**
	 * 年龄
	 */
	private int age;
	
	/**
	 * 生日
	 */
	private Date birthday;
	
	/**
	 * 客户类型
	 */
	private int type;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 座机号
	 */
	private String telephone;
	
	/**
	 * QQ
	 */
	private String qq;
	
	/**
	 * email
	 */
	private String email;
	
	/**
	 * 余额
	 */
	private double balance;
	
	/**
	 * 已消费额
	 */
	private double cost;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}


}
