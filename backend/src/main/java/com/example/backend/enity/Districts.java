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
@Table(name="districts")
public class Districts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String name_en;
    private String full_name;
    private String full_name_en;
    private String code_name;
    @Column(name = "province_id")
    private Long provinceId;
    private Long administrative_unit_id;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime created_at;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updated_at;
}
