package com.megane.usermanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Bill extends TimeAuditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

//	@CreatedDate // tu gen
//	@Column(updatable = false)
//	private Date buyDate;
	
	private String status;//NEW, PENDING, ACTIVE

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, 
			orphanRemoval = true)
	private List<BillItem> billItems;
}
