package com.textbookvalet.commons;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings({ "serial" })
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Application /* extends BaseTracebleModel */ implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SerializedName("id")
	private Integer id;

	@Column(name = "aasmState")
	private String state;

	private String name;

	private String email;

	private String phone;

	private Integer schoolId;

	@Type(type = "text")
	private String involvement;

	@Type(type = "text")
	private String whyWorkFor;

	private String profilePicture;

	private String file;

	private String role;

	private String schoolName;

	private Integer userId;

	private Integer housing;

	private Integer phoneType;

	private String referral;

	private Integer highrisePersonId;

	private String source;

	private String dateApplied;

	private Integer attempts;

	private Integer referrerId;

	private String referrerType;

	private String paypalEmail;

	private Timestamp createdAt = new Timestamp(new Date().getTime());

	private Timestamp updatedAt = new Timestamp(new Date().getTime());

}
