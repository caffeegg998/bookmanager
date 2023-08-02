package com.megane.usermanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.megane.usermanager.entity.Category;
import jakarta.persistence.Lob;
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

    private String title;
    private String author;
    private String lang;
    private String publisher;
    private int publicationYear;
    private String subject;//tags
    private String bookCreator;

    private String description;
    private String format;
    private String series;

    private String bookUrl;
    private String coverUrl;

    @JsonIgnore
    private MultipartFile file;

    @JsonIgnore
    private MultipartFile file2;

    private Date createdAt;//java.util
    private Date updatedAt;

    // Constructors, getters, and setters

    // ...
}

