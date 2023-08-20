package com.megane.usermanager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user",uniqueConstraints = {
        //Cái này để định nghĩa constraint key cho cột trong bảng
        //Không bị conflict thì check trong ExceptionController
        @UniqueConstraint(name = "uniqueUsername", columnNames = {"username"}),
        @UniqueConstraint(name = "UniqueEmail", columnNames = {"email"})
})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;


    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private String homeAddress;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;

    private boolean isEnabled = false;

}
