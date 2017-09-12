package com.textbookvalet.rest.v1.dto;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data 
public class SchoolRequestDTO implements Serializable {
	private int page;
	private int per_page; 

}
