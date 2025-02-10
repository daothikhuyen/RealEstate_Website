package com.example.backend.service.Feedbacks;

import com.example.backend.dto.request.Feedback.FeedbackRequest;
import com.example.backend.dto.response.Feedbacks.FeedbackResponse;
import com.example.backend.enity.Feedbacks;
import com.example.backend.enity.User;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.mapper.Feedbacks.FeedbackMapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.reponsitory.Feedbacks.FeedbackRepository;
import com.example.backend.reponsitory.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // lombok tạo các contructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // đưa các final thành private nếu null
public class FeedbackService {

    FeedbackRepository feedbackRepository;
    FeedbackMapper feedbackMapper;
    UserRepository userRepository;
    UserMapper userMapper;

    public FeedbackResponse getFeeback_ByPostId(Long postId) {

        List<Feedbacks> list_feedbackResponse = feedbackRepository.findByPostIdAndIsDeleted(postId, 0);
        List<FeedbackResponse> responses = Recursive(list_feedbackResponse, 0L);

        return FeedbackResponse.builder()
                .total_feedback(list_feedbackResponse.size())
                .feedback(responses)
                .build();
    }

    public List<FeedbackResponse> Recursive(List<Feedbacks> feedbackList, Long parentId){

        List<FeedbackResponse> arrayList = new ArrayList<>();

        for (Feedbacks feedback : feedbackList){

            if(feedback.getParentId() == parentId){

                List<FeedbackResponse> children = Recursive(feedbackList,feedback.getId());

                FeedbackResponse feedbackResponse = feedbackMapper.toFeedbackResponse(feedback);
                if(!children.isEmpty()){
                    feedbackResponse.setFeedback(children);
                }

                User user = userRepository.findById(feedback.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

                feedbackResponse.setUser(userMapper.toUserResponse(user));

                arrayList.add(feedbackResponse);
            }
        }

        return arrayList;

    }

    public FeedbackResponse insertFeedback(FeedbackRequest request) {

        Feedbacks feedback = feedbackMapper.toFeedback(request);

        feedback.setUserId(request.getUserId());
        feedback.setParentId(request.getParentId());
        feedback.setComment(request.getComment());
        feedback.setParentId(request.getParentId());
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setUpdatedAt(LocalDateTime.now());

        try {
            feedback = feedbackRepository.save(feedback);

            return feedbackMapper.toFeedbackResponse(feedback);
        }catch (DataIntegrityViolationException e){
            throw new AppException((ErrorCode.UNCATEGORIZED_EXCEPTION));
        }

    }

    public String destroyFeedbackId(Long feedbackId) {

        Feedbacks feedback = feedbackRepository.findById(feedbackId).orElseThrow(() ->  new AppException(ErrorCode.FEEDBACK_NOT_EXISTED));

        feedback.setIsDeleted(1);
        feedbackRepository.save(feedback);
        return "Xoá thành công";

    }
}
