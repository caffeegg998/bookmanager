package com.megane.usermanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

//GRAPQL tham khao cai nay
@Data
public class BillDTO {
	private Integer id;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date createdAt;
	private UserDTO user;
	
//	@JsonManagedReference
	private List<BillItemDTO> billItems;
}
