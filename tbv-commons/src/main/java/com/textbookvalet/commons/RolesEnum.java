package com.textbookvalet.commons;

public enum RolesEnum {

	ADMIN(1, "ADMIN", "admin"), 
	CUSTOMER(2, "CUSTOMER", "customer"),
	OWNER(3, "OWNER", "owner"),
	OPERATIONS_MANAGER(4, "OPERATIONS MANAGER", "operations_manager"),
	CAMPUS_MANAGER(5, "CAMPUS MANAGER", "campusmanager"),
	CAMPUS_REP(6, "CAMPUS REP", "campusrep"); 

	private int id;
	private String name;
	private String value;

	RolesEnum(int id, String name, String value) {
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
