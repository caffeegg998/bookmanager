package com.megane.usermanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.megane.usermanager.entity.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO {
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private int id;


    @NotBlank
    private String fullName;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private String homeAddress;

    private List<RoleDTO> roles;
}
