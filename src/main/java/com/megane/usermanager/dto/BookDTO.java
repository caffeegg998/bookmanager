package com.megane.usermanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.megane.usermanager.entity.Category;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class BookDTO {
    private int id;

    private List<Category> category;

    @NotBlank
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;

    private String isbn;
    private int quantityInStock;
    private int quantitySold;

    private String bookFilePath;

    @JsonIgnore
    private MultipartFile file;

    private Date createdAt;//java.util
    private Date updatedAt;

    // Constructors, getters, and setters

    // ...
}

