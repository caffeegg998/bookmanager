package com.megane.usermanager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<Role> roles;

    private boolean isEnabled = false;

}
