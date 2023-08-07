package com.megane.usermanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.megane.usermanager.entity.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO {
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private int id;


    @NotBlank(message = "Tên đầy đủ không được bỏ trống.")
    private String fullName;


    @Column(unique = true)
    @Size(min = 6, max = 30, message = "Độ dài Username phải nằm trong khoảng 6 đến 30 ký tự.")
    @NotBlank(message = "Tên đăng nhập không được bỏ trống.")
    private String username;

    @Size(min = 6, max = 10, message = "Độ dài Password phải nằm trong khoảng 6 đến 10 ký tự.")
    @NotBlank(message = "Mật khẩu không được bỏ trống.")
    private String password;

    @Email(message = "Định dạng email không chính xác!")
    @NotBlank(message = "Email không được bỏ trống.")
    private String email;
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/YYYY")
    private Date birthDate;
    private String homeAddress;

    private List<RoleDTO> roles;

    private boolean isEnabled = false;
}
