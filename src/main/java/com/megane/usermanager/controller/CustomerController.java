package com.megane.usermanager.controller;

import com.megane.usermanager.dto.*;
import com.megane.usermanager.service.itfmethod.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/")
    public ResponseDTO<Void> create(@RequestBody @Valid CustomerDTO customerDTO){
        customerService.create(customerDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("ok")
                .build();
    }
    @GetMapping("/") // ?id=1000
    @ResponseStatus(code = HttpStatus.OK)
    // @Secured({"ROLE_ADMIN","ROLE_SYSADMIN"}) //ROLE_   //hAI dONG NAY GIONG NHAU //Bao mat tren ham
    // @RolesAllowed({"ROLE_ADMIN","ROLE_SYSADMIN"})		  //
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")		  //
    //@PreAuthorize("isAuthenticated()")			      //
    public ResponseDTO<CustomerDTO> get(
            @RequestParam("id") int id) {
        return ResponseDTO.<CustomerDTO>builder()
                .status(200)
                .msg("OK!")
                .data(customerService.getById(id))
                .build();
    }

    @GetMapping("/list")
    public ResponseDTO<List<CustomerDTO>> list() {
        List<CustomerDTO> departmentDTOs = customerService.getAll();
        return ResponseDTO.<List<CustomerDTO>>builder().status(200).data(departmentDTOs).build();
    }
//    @PutMapping("/")
//    public ResponseDTO<Void> edit(@RequestParam @Valid CustomerDTO customerDTO){
//        customerService.update(customerDTO);
//        return ResponseDTO.<Void>builder()
//                .status(200)
//                .msg("ok")
//                .build();
//
//    }
    @PostMapping("/search") // jackson
    public ResponseDTO<PageDTO<List<CustomerDTO>>>
    search(@RequestBody @Valid SearchDTO searchDTO) {
        PageDTO<List<CustomerDTO>> pageDTO = customerService.search(searchDTO);

        return ResponseDTO.
                <PageDTO<List<CustomerDTO>>>builder()
                .status(200)
                .data(pageDTO)
                .build();
    }
}
