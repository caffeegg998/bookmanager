package com.megane.usermanager.registration.password;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetRequest {

    private String email;
    private String oldPassword;

    @Size(min = 8, max = 32, message = "Độ dài Password phải nằm trong khoảng 8 đến 32 ký tự.")
    @NotBlank(message = "Mật khẩu không được bỏ trống.")
    private String newPassword;


}
