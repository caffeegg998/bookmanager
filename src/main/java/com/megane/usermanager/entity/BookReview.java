package com.megane.usermanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class BookReview extends TimeAuditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String content;
	private int star;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	private Book book;
}
