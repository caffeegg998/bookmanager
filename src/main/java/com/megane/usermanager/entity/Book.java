package com.megane.usermanager.entity;


import java.util.Date;
import java.util.List;

import jakarta.persistence.*;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;



@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany
    private List<Category> category;

    private String title;
    private String author;
    private String publisher;
    private int publicationYear;

    @Column(unique = true)
    private String isbn;
    private int quantityInStock;
    private int quantitySold;

    @CreatedDate //auto gen new date
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    // Constructors, getters, and setters

    // ...
}
