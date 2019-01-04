package com.oauth2.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AbstractEntity extends AbstractPersistable<Long> implements Cloneable {

	private static final long serialVersionUID = 5583035582769043365L;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate = new Date();

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate = new Date();

	@CreatedBy
	private String createdBy;

	@LastModifiedBy
	private String lastModifiedBy;

}
