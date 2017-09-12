package com.textbookvalet.commons;

public enum TypesEnum {

	ADMIN(1, "ADMIN", "Admin"), 
	CUSTOMER(2, "CUSTOMER", "Customer"); 

	private int id;
	private String name;
	private String value;

	TypesEnum(int id, String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
