package com.textbookvalet.commons;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings({ "serial" })
@Entity
@Table(name = "device_token")
@Data
@EqualsAndHashCode(callSuper = true)
public class AccessToken extends BaseModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SerializedName("id")
	private Integer id;

	private Integer userId;

	private Timestamp createdAt = new Timestamp(new Date().getTime());

	private String token;

}
