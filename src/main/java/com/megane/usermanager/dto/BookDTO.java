package com.megane.usermanager.dto;

import com.megane.usermanager.entity.Category;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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


    private Date createdAt;//java.util
    private Date updatedAt;

    // Constructors, getters, and setters

    // ...
}

