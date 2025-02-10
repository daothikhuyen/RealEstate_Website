package com.example.backend.enity;

import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob // Chỉ định đây là một đối tượng lớn
    private String description;
    @Column(name = "price", columnDefinition = "DOUBLE")
    private double price;
    @Column(name = "area", columnDefinition = "DOUBLE")
    private double area;
    private String full_address;
    @Column(name = "user_id")
    private Long userId;
    private Long category_id;
    @Column(name = "number_like", columnDefinition = "INT")
    private int number_like;
    @Column(name = "province_id")
    private Long provinceId;
    @Column(name = "district_id")
    private Long districtId;
    @Column(name = "ward_id")
    private Long wardId;
    @Column(name = "is_deleted", columnDefinition = "INT")
    private int isDeleted;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime created_at;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updated_at;


}
