package com.megane.usermanager.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
public class Customer {
    @Id
    private int userId;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @MapsId //copy id user set cho id cua student
    private User user;//user_id
    private String customerCode;
    // Constructors, getters, and setters

    // ...
}

