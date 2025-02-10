package com.example.backend.mapper.Feedbacks;

import com.example.backend.dto.request.Feedback.FeedbackRequest;
import com.example.backend.dto.response.Feedbacks.FeedbackResponse;
import com.example.backend.enity.Feedbacks;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    Feedbacks toFeedback(FeedbackRequest request);

    FeedbackResponse toFeedbackResponse(Feedbacks feedbacks);
}
