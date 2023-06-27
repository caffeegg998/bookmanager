package com.megane.usermanager.controller;

import com.megane.usermanager.dto.ResponseDTO;
import com.megane.usermanager.dto.UserDTO;
import com.megane.usermanager.service.itfmethod.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/")
    public ResponseDTO<Void> newUser(@ModelAttribute @Valid UserDTO userDTO) throws IllegalStateException, IOException {
        userService.create(userDTO);
        return ResponseDTO.<Void>builder().status(200).msg("ok").build();
    }
    @DeleteMapping("/")
    public ResponseDTO<Void> delete(@RequestParam("id") int id){
        userService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("Delete Success").build();
    }
    @PutMapping("/edit")
    public ResponseDTO<Void> edit(@RequestBody @Valid UserDTO userDTO) throws IOException{
        userService.update(userDTO);
        return ResponseDTO.<Void>builder().status(200).msg("Update Success").build();
    }
    @GetMapping("/")
    public ResponseDTO<UserDTO> getById(@RequestParam("id") int id){
        return ResponseDTO.<UserDTO>builder().status(200).data(userService.getById(id)).build();
    }
    @GetMapping("/list")
    public ResponseDTO<List<UserDTO>> list() {
        List<UserDTO> userDTOS = userService.getAll();
        return ResponseDTO.<List<UserDTO>>builder().status(201).data((userDTOS)).build();
    }
}
