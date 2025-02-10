package com.example.backend.reponsitory;

import com.example.backend.enity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long>, JpaSpecificationExecutor<Post> {


    Page<Post> findAllByIsDeleted(int isDeleted, Pageable pageable);
    Page<Post> findByUserIdAndIsDeleted(Long userId, int isDeleted, Pageable pageable);

    List<Post> findByProvinceId(Long provinceId);

    List<Post> findByProvinceIdAndDistrictId(Long provinceId, Long districtId);

    List<Post> findByProvinceIdAndDistrictIdAndWardId(Long provinceId, Long districtId,Long wardId);

    @Query("SELECT p FROM Post p WHERE p.full_address LIKE %:input% OR p.title LIKE %:input%")
    List<Post> searchInputAll(@Param("input") String input);

    @Query("SELECT p FROM Post p WHERE p.isDeleted = 0 AND p.title LIKE %:input% ORDER BY p.id DESC")
    Page<Post> findTitleSuggestions(@Param("input") String input, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.isDeleted = 0 AND p.full_address LIKE %:input% ORDER BY p.id DESC")
    Page<Post> findFullAddressSuggestions(@Param("input") String input, Pageable pageable);

    List<Post> findAllByOrderByIdDesc();
}
