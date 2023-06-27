package com.megane.usermanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullName;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private String homeAddress;

}
