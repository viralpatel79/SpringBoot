package com.textbookvalet.commons;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings({ "serial" })
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Event extends BaseTracebleModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SerializedName("id")
	private Integer id;

	private String location;

	private Integer userId;

	private Timestamp startDate = new Timestamp(new Date().getTime());

	private Timestamp endDate;

	private Integer schoolId;

}
