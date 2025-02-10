package com.example.backend.reponsitory.Feedbacks;

import com.example.backend.enity.RattingFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RattingFeedbackRepository extends JpaRepository<RattingFeedback,Long> {

    List<RattingFeedback> findByFeedbackId(Long feedback_id);

    Optional<RattingFeedback> findByUserIdAndFeedbackId(Long userIdm ,Long feedbackId);

}

