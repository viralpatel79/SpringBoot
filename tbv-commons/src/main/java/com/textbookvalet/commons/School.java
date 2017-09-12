package com.textbookvalet.commons;

import java.io.Serializable;
import java.math.BigDecimal;

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
@EqualsAndHashCode(callSuper = true)
public class School extends BaseTracebleModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SerializedName("id")
	private Integer id;

	private String name;

	private String offCampusImage;

	private String offCampusWhere;

	private String offCampusTime;

	@Type(type = "text")
	private String buyBack;

	private String buyBackImage;

	private String fullName;

	private boolean buyBackState = true;

	private String schoolLink;

	private String stripeUserId;

	private String currency;

	@Type(type = "text")
	private String aboutUs;

	private Float salesTax;

	@Column(precision = 6, scale = 3)
	private BigDecimal commission;

	@Type(type = "text")
	private String embedCode;

	@Type(type = "text")
	private String closedDialogDescription;

	private String closedDialogLinkText;

	private String closedDialogLinkUri;

	private String image;

	private String requestedCalendarId;

	private String confirmedCalendarId;

	private boolean estimateTool = true;

	private String buyingState;

	private Integer cityId;

	private int paymentMethod = 1;

	private boolean localDelivery = false;

	private boolean appointmentsEnabled = false;

}
