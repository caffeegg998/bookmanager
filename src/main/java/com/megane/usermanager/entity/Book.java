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
public class Book extends TimeAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany
    private List<Category> category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Rating> ratings;


    private String title;
    private String author;
    private String lang;
    private String publisher;
    private int publicationYear;
    private String subject;//tags

    @Column(length = 4000)
    private String description;
    private String format;
    private String series;

    private String coverUrl;
    private String bookUrl;
    private String bookCreator;

    private int downloadCount;
//    @Column(unique = true)
//    private String isbn;
//    private int quantityInStock;
//    private int quantitySold;

//    @CreatedDate //auto gen new date
//    @Column(updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
//
//    @LastModifiedDate
//	@Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
    // Constructors, getters, and setters

    // ...
}
