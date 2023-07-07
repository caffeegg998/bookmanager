package com.megane.usermanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class BillItemDTO {
	private Integer id;

//	@JsonBackReference
	@JsonIgnoreProperties("billItems")
	private BillDTO billDTO;

	private BookDTO book;

//	@Min(0)
	private int quantity;
//	@Min(0)
	private double price;
}
