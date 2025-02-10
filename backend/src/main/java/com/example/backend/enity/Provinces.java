package com.example.backend.enity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="provinces")
public class Provinces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String name_en;
    private String full_name;
    private String full_name_en;
    private String code_name;
    private Long administrative_unit_id;
    private Long administrative_region_id;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime created_at;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updated_at;
}
