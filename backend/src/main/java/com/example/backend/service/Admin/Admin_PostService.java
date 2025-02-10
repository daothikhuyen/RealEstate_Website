package com.example.backend.service.Admin;

import com.example.backend.dto.response.PostDetailResponse;
import com.example.backend.enity.Post;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.reponsitory.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor // lombok tạo các contructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // đưa các final thành private nếu null
@Slf4j
public class Admin_PostService {

    PostRepository postRepository;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Map<String, Object> getListPosts(int page) {

        try {

            Pageable paging = PageRequest.of(page -1,5);
            Page<Post> pagePosts = postRepository.findAllByIsDeleted(0,paging);

            Map<String, Object> response = new HashMap<>();
            response.put("paginate", Map.of(
                    "total", pagePosts.getTotalElements(),
                    "per_page", pagePosts.getSize(),
                    "current_page", pagePosts.getNumber() + 1,
                    "last_page", pagePosts.getTotalPages(),
                    "from", pagePosts.hasContent() ? pagePosts.getContent().get(0).getId() : null,
                    "to", pagePosts.hasContent() ? pagePosts.getContent().get(pagePosts.getContent().size() - 1).getId() : null
            ));
            response.put("data", pagePosts.getContent());

            return response;
        } catch (Exception e) {
            e.getMessage();
            throw new AppException(ErrorCode.POST_NOT_EXISTED);
        }
    }




}
