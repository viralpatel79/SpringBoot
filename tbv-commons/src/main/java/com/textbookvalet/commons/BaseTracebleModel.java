package com.textbookvalet.commons;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings({ "serial" })
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseTracebleModel extends BaseModel implements Serializable {

	private Timestamp createdAt = new Timestamp(new Date().getTime());
	private Timestamp updatedAt = new Timestamp(new Date().getTime());
	/*
	 * private long createdBy; private long updatedBy;
	 */

	private Boolean deleted = false;
}
