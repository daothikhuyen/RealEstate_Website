package com.example.backend.mapper;

import com.example.backend.dto.request.Feedback.RattingRequest;
import com.example.backend.dto.response.RattingResponse;
import com.example.backend.enity.RattingFeedback;
import com.example.backend.enity.RattingPost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")// MapStruct sẽ tạo ra implementation tự động
public interface RattingMapper {

    RattingFeedback toRattingFeedback(RattingRequest request);
    RattingPost toRattingPost(RattingRequest request);

//    RattingPost toRattingPostResponse(RattingPost rattingPost);
//
//    RattingFeedback toRattingFeedbackResponse (RattingFeedback rattingFeedback);
}
