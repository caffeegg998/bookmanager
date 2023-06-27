package com.megane.usermanager.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CustomerDTO {
    private UserDTO user;
    private String customerCode;



    // Constructors, getters, and setters

    // ...
}

