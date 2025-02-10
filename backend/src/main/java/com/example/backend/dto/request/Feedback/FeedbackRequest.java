package com.example.backend.dto.request.Feedback;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackRequest {

    private Long userId;
    private Long postId;
    private String comment;
    private int parentId;


}
