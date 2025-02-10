package com.example.backend.reponsitory;

import com.example.backend.enity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Images,Long> {

    List<Images> findByPostId(Long postId);

    @Transactional
    @Modifying // Bắt buộc phải có đối với các câu lệnh không phải SELECT.
    @Query("DELETE FROM Images p WHERE postId = :postId AND p.id NOT IN :ids")
    int deleteImagesNotInIds(@Param("postId") Long postId, @Param("ids") List<Long> ids);

    @Transactional
    @Modifying
    @Query("DELETE FROM Images p WHERE postId = :postId ")
    int deleteByPostId(@Param("postId") Long postId);
}
