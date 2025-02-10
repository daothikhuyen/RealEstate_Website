package com.example.backend.enity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="password_reset_token")
public class PasswordRestToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String token;

    @Column(name = "creatd_at")
    private Date creatdAt ;

}
