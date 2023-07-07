package com.megane.usermanager.controller;

import com.megane.usermanager.dto.ResponseDTO;
import com.megane.usermanager.dto.RoleDTO;
import com.megane.usermanager.service.interf.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping("/")
    public ResponseDTO<Void> create(@ModelAttribute @Valid RoleDTO roleDTO){
        roleService.create(roleDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Success!").build();
    }
    @DeleteMapping("/")
    public ResponseDTO<Void> delete(@RequestParam("id") int id){
        roleService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Delete Success!")
                .build();
    }
    @GetMapping("/list")
    public ResponseDTO<List<RoleDTO>> list(){
        List<RoleDTO> roleDTOS = roleService.getAll();
        return ResponseDTO.<List<RoleDTO>>builder()
                .status(200)
                .data(roleDTOS)
                .build();
    }
}
