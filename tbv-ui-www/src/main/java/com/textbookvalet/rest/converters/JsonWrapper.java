package com.textbookvalet.rest.converters;

public class JsonWrapper {
	
	private String status = "success";
	private Object data;
	
	public JsonWrapper(Object data) {
		super(); 
		this.data = data;
	}
	
	public JsonWrapper(String status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	} 
	
}
