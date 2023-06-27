package com.megane.usermanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Staff {
    @Id
    private int userId;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @MapsId //copy id user set cho id cua student
    private User user;//user_id

    private String staffCode;

}
