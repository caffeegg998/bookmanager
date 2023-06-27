package com.megane.usermanager.controller;

import com.megane.usermanager.dto.StaffDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.ResponseDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.service.itfmethod.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    StaffService staffService;

    @PostMapping("/")
    public ResponseDTO<Void> create(@RequestBody @Valid StaffDTO staffDTO){
        staffService.create(staffDTO);
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
    public ResponseDTO<StaffDTO> get(
            @RequestParam("id") int id) {
        return ResponseDTO.<StaffDTO>builder()
                .status(200)
                .msg("OK!")
                .data(staffService.getById(id))
                .build();
    }

    @GetMapping("/list")
    public ResponseDTO<List<StaffDTO>> list() {
        List<StaffDTO> departmentDTOs = staffService.getAll();
        return ResponseDTO.<List<StaffDTO>>builder().status(200).data(departmentDTOs).build();
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
    public ResponseDTO<PageDTO<List<StaffDTO>>>
    search(@RequestBody @Valid SearchDTO searchDTO) {
        PageDTO<List<StaffDTO>> pageDTO = staffService.search(searchDTO);

        return ResponseDTO.
                <PageDTO<List<StaffDTO>>>builder()
                .status(200)
                .data(pageDTO)
                .build();
    }
}
