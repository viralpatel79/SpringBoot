package com.textbookvalet.commons;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings({ "serial" })
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseTracebleModel implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referrer_id")
	private User user;

	private Integer userId;

	private Integer quantity;

	private Integer total;

	@Temporal(TemporalType.TIMESTAMP)
	private Date delivery;

	private String referral;

	private Integer promoId;

	private Integer termId;

	private Integer schoolId;

	@Type(type = "text")
	private String notes;

	private Integer parentOrderId;

	private Integer referralAdmin;

	private Integer organizationId;

	private String dropoffLocation;

	private Boolean seen;

	private Integer deliveryPersonId;

	private String deliveryStatus;

	private String roommateContactInfo;

	@Type(type = "text")
	private String specialInstructions;

	private Boolean rush;

	@Temporal(TemporalType.TIMESTAMP)
	private Date standardDeliveryTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date rushDeliveryTime;

	private Boolean deliverLater;

	private int deliveryPriceCents;

	private String deliveryPriceCurrency;

	private Integer shippingAddressId;

	private Boolean shipment;

	private Integer shippingMethod;

	private Integer referrerCommission;

	private Boolean ifPaid;

	private Integer taxes;

	private Integer savings;

	private Integer subtotal;

	private Integer leadId;

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders") public
	 * Set<RepQueueItems> getRepQueueItemses() { return this.repQueueItemses; }
	 * 
	 * public void setRepQueueItemses(Set<RepQueueItems> repQueueItemses) {
	 * this.repQueueItemses = repQueueItemses; }
	 */

}
