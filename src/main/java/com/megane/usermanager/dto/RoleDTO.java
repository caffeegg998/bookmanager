package com.megane.usermanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleDTO {

    private Integer id;

    @NotBlank
    @Size(min = 6, max = 20)
    private String name;
}
