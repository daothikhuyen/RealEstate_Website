package com.example.backend.service.Feedbacks;

import com.example.backend.dto.request.Feedback.RattingRequest;
import com.example.backend.dto.response.RattingResponse;
import com.example.backend.enity.RattingFeedback;
import com.example.backend.enity.RattingPost;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.mapper.RattingMapper;
import com.example.backend.reponsitory.Feedbacks.RattingFeedbackRepository;
import com.example.backend.reponsitory.Feedbacks.RattingPostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // lombok tạo các contructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // đưa các final thành private nếu null
public class RattingService {

    RattingFeedbackRepository rattingFeedbackRepository;
    RattingPostRepository rattingPostRepository;
    RattingMapper rattingMapper;

    public List<RattingPost> getLikePost() {

        return rattingPostRepository.findAll().stream().toList();
    }

    public List<RattingFeedback> getLikeFeedback(RattingFeedback request) {

        return rattingFeedbackRepository.findByFeedbackId(request.getFeedbackId()).stream().toList();
    }

    public RattingResponse likePost(RattingRequest request){

        RattingPost rattingPost = rattingMapper.toRattingPost(request);

        Optional<RattingPost> existingLike = rattingPostRepository.findByUserIdAndPostId(rattingPost.getUserId(), rattingPost.getPostId());

        try {
            if(!existingLike.isPresent()){

                rattingPost.setUserId(rattingPost.getUserId());
                rattingPost.setPostId(rattingPost.getPostId());
                rattingPost.setLiked(rattingPost.getLiked());
                rattingPost.setCreatedAt(LocalDateTime.now());
                rattingPost.setUpdatedAt(LocalDateTime.now());

                rattingPost = rattingPostRepository.save(rattingPost);


                return new RattingResponse("likes", "Yêu thích thành công");
            }else{
                rattingPostRepository.delete(existingLike.get());

                return new RattingResponse("unlike", "Xoá yêu thích thành công");
            }
        }catch (DataIntegrityViolationException e){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }


    public RattingResponse likeFeedback(RattingRequest request){

        RattingFeedback rattingFeedback = rattingMapper.toRattingFeedback(request);

        Optional<RattingFeedback> existingLike = rattingFeedbackRepository.findByUserIdAndFeedbackId(rattingFeedback.getUserId(), rattingFeedback.getFeedbackId());

        try {
            if(!existingLike.isPresent()){

                rattingFeedback.setUserId(rattingFeedback.getUserId());
                rattingFeedback.setFeedbackId(rattingFeedback.getFeedbackId());
                rattingFeedback.setLiked(rattingFeedback.getLiked());
                rattingFeedback.setCreatedAt(LocalDateTime.now());
                rattingFeedback.setUpdatedAt(LocalDateTime.now());

                rattingFeedback = rattingFeedbackRepository.save(rattingFeedback);

                rattingFeedbackRepository.save(rattingFeedback);

                return new RattingResponse("like", "Yêu thích thành công");
            }else{
                rattingFeedbackRepository.delete(existingLike.get());

                return new RattingResponse("unlike", "Xoá yêu thích thành công");
            }
        }catch (DataIntegrityViolationException e){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

    }

}
