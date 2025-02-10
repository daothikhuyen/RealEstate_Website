package com.example.backend.controller.Feedbacks;


import com.example.backend.dto.request.Feedback.FeedbackRequest;
import com.example.backend.dto.request.Feedback.RattingRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.Feedbacks.FeedbackResponse;
import com.example.backend.dto.response.RattingResponse;
import com.example.backend.enity.Feedbacks;
import com.example.backend.service.Feedbacks.FeedbackService;
import com.example.backend.service.Feedbacks.RattingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feedback")
@Slf4j // .log
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @GetMapping("getFeedbacks/{postId}")
    public ApiResponse<List<FeedbackResponse>> getFeedback(@PathVariable String postId){

        FeedbackResponse response = feedbackService.getFeeback_ByPostId(Long.valueOf(postId));

        return ApiResponse.<List<FeedbackResponse>>builder()
                .message(String.valueOf(response.getTotal_feedback()))
                .result(response.getFeedback())
                .build();

    }

    @PostMapping("/insert")
    public ApiResponse<FeedbackResponse> insertFeedback(@RequestBody FeedbackRequest request){

        FeedbackResponse insertFeedback = feedbackService.insertFeedback(request);

        return ApiResponse.<FeedbackResponse>builder()
                .code(1000)
                .message("Thành công")
                .result(insertFeedback)
                .build();
    }

    @PostMapping("admin/list_feedback/{postId}")
    public ApiResponse<List<FeedbackResponse>> admin_listFeedback(@PathVariable String postId){
        FeedbackResponse response = feedbackService.getFeeback_ByPostId(Long.valueOf(postId));

        return ApiResponse.<List<FeedbackResponse>>builder()
                .message(String.valueOf(response.getTotal_feedback()))
                .result(response.getFeedback())
                .build();
    }

    @PostMapping("admin/destroyFeedback/{feedbackId}")
    public ApiResponse<String> admin_destroyFeedback(@PathVariable Long feedbackId){

        String response = feedbackService.destroyFeedbackId(feedbackId);

        return ApiResponse.<String>builder()
                .result(response)
                .build();

    }




}
