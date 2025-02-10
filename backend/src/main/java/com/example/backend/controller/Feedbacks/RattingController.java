package com.example.backend.controller.Feedbacks;

import com.example.backend.dto.request.Feedback.RattingRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.RattingResponse;
import com.example.backend.enity.RattingFeedback;
import com.example.backend.enity.RattingPost;
import com.example.backend.service.Feedbacks.RattingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ratting")
@Slf4j // .log
public class RattingController {

    @Autowired
    RattingService rattingService;

    @GetMapping("/getLikePost")
    public ApiResponse<List<RattingPost>> getLikePost(){

        return ApiResponse.<List<RattingPost>>builder()
                .result(rattingService.getLikePost())
                .build();

    }

    @PostMapping("/getlikeFeedback")
    public ApiResponse<List<RattingFeedback>> getlikeFeddback(@RequestBody RattingFeedback request){

        return ApiResponse.<List<RattingFeedback>>builder()
                .result(rattingService.getLikeFeedback(request))
                .build();

    }

    @PostMapping("/likePost")
    public ApiResponse<RattingResponse> likePost(@RequestBody RattingRequest request){

        RattingResponse rattingResponse = rattingService.likePost(request);

        return ApiResponse.<RattingResponse>builder()
                .code(1000)
                .result(rattingResponse)
                .build();

    }

    @PostMapping("/likeFeedback")
    public ApiResponse<RattingResponse> likeFeedback(@RequestBody RattingRequest request){

        RattingResponse rattingResponse = rattingService.likeFeedback(request);

        return ApiResponse.<RattingResponse>builder()
                .code(1000)
                .result(rattingResponse)
                .build();

    }
}
