package com.megane.usermanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeAuditable {

	@CreatedDate // auto gen new date
	@Column(updatable = false)
	private Date createdAt;// java.util

	@LastModifiedDate
	private Date updatedAt;
}
