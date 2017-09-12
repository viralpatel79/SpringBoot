package com.textbookvalet.rest.v1.dto;

import com.textbookvalet.commons.annotations.CustomResponse;

import lombok.Data;

@CustomResponse
public @Data class FileUploadDTO {

	private boolean uddated;

	private String url;

	private long size;

}
