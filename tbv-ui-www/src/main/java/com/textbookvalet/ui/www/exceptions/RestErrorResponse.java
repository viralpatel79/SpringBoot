package com.textbookvalet.ui.www.exceptions;

/*
 * 
 {
  "status": "fail",
  "data": {
    "message": "The username and or password do not match with our records."
  }
}
 */
public class RestErrorResponse {
	private String status = "success";
	private Data data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	} 
}

