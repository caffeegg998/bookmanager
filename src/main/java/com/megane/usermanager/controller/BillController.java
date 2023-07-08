package com.megane.usermanager.controller;

import com.megane.usermanager.dto.*;
import com.megane.usermanager.repo.BillRepo;
import com.megane.usermanager.service.MailService;
import com.megane.usermanager.service.interf.BillService;
import com.megane.usermanager.service.interf.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class BillController {

	@Autowired
	BillService billService;

	@Autowired
	MailService mailService;

	@Autowired
	UserService userService;

	@Autowired
	BillRepo billRepo;

	// admin tao don cho khach hang
	/*
	 * {
		    "user": {
		        "id": 6
		    },
		    "billItems": [
		        {
		            "quantity":10,
		            "price": 5000,
		            "product": {
		                "id": 2
		            }
		        },
		        {
		            "quantity":5,
		            "price": 1000,
		            "product": {
		                "id": 3
		            }
		        }
		    ]
		}
	 */
	@PostMapping("/admin/bill")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseDTO<BillDTO> add(@RequestBody @Valid BillDTO billDTO) {
		billService.create(billDTO);
		mailService.sendEmail(billDTO.getUser().getEmail(), "BILL ID " + billDTO.getId(), "Đơn hàng của bạn đươc tạo thành công");
		return ResponseDTO.<BillDTO>builder().status(200).data(billDTO).build();
	}
	
	//tu tay khach hang tao don
	/*
	 * {
		    "billItems": [
		        {
		            "quantity":10,
		            "price": 5000,
		            "product": {
		                "id": 2
		            }
		        },
		        {
		            "quantity":5,
		            "price": 1000,
		            "product": {
		                "id": 3
		            }
		        }
		    ]
		}
	 */
	@PostMapping("/customer/bill")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseDTO<BillDTO> add(@RequestBody @Valid BillDTO billDTO,
			Principal p) {
		SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = p.getName();
		UserDTO user = userService.findByUsername(username);
		billDTO.setUser(user);
		
		billService.create(billDTO);
		mailService.sendEmail("caffeegg998@gmail.com", "BILL ID " + billDTO.getId(), "Đơn hàng của bạn đươc tạo thành công");
		return ResponseDTO.<BillDTO>builder().status(200).data(billDTO).build();
	}

	@GetMapping("/search")
	public ResponseDTO<PageDTO<List<BillDTO>>> search(
			@RequestBody @Valid SearchDTO searchDTO) {
		return ResponseDTO.<PageDTO<List<BillDTO>>>builder()
				.status(200)
				.data(billService.search(searchDTO))
				.build();
	}

	@GetMapping("/{id}") // 10
	public ResponseDTO<BillDTO> get(@PathVariable("id") int id) {
		BillDTO BillDTO = billService.getById(id);
		return ResponseDTO.<BillDTO>builder().status(200).data(BillDTO).build();
	}

	@GetMapping("/statistic") // 10
	public ResponseDTO<PageDTO<List<BillStatisticDTO>>> get() {
		PageDTO<List<BillStatisticDTO>> pageRS = billService.statistic();
		return ResponseDTO.<PageDTO<List<BillStatisticDTO>>>builder().status(200).data(pageRS).build();
	}

	@DeleteMapping("/{id}") // /1
	public ResponseDTO<Void> delete(@PathVariable("id") int id) {
		billService.delete(id);
		return ResponseDTO.<Void>builder().status(200).build();
	}

	@PutMapping("/")
	public ResponseDTO<Void> update(@RequestBody @Valid BillDTO bill) {
		billService.update(bill);
		return ResponseDTO.<Void>builder().status(200).build();
	}
}
