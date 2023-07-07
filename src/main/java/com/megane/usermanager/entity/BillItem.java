package com.megane.usermanager.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class BillItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Bill bill;

	@ManyToOne
	private Book book;

	private int quantity;
	private double price;
}
