package com.textbookvalet.commons;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings({ "serial" })
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Cashout implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;

	private Integer adminId;

	private Integer amount;

	private Timestamp createdAt = new Timestamp(new Date().getTime());

	private Timestamp updatedAt = new Timestamp(new Date().getTime());

}
