package com.megane.usermanager.dto;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CustomerDTO {
    @Valid
    private UserDTO user;


    private String customerCode;



    // Constructors, getters, and setters

    // ...
}

