package com.example.backend.enity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="verifytokens")
public class Verifytokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String email;
    @Column(name = "is_activated", columnDefinition = "INT DEFAULT 0")
    private int isActivated;

}
