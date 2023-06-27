package com.megane.usermanager.dto;

import com.megane.usermanager.entity.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private int id;
    @NotBlank
    private String name;
}
