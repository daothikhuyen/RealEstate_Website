package com.example.backend.reponsitory.Feedbacks;

import com.example.backend.enity.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedbacks,Long> {

    List<Feedbacks> findByPostIdAndIsDeleted(Long postId, int isDeleted);

}
