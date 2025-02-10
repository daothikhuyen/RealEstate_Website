package com.example.backend.dto.response.Feedbacks;

import com.example.backend.dto.response.UserResponse;
import com.example.backend.enity.Feedbacks;
import com.example.backend.enity.User;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackResponse {

    private Long id;
    private Long postId;
    private String comment;
    private int parentId;
    private LocalDateTime createdAt;
    private List<FeedbackResponse> feedback;
    private UserResponse user;
    private int total_feedback;


//    User user;


}
