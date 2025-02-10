package com.example.backend.dto.response;

import com.example.backend.enity.Extensions;
import com.example.backend.enity.Images;
import com.example.backend.enity.Post;
import com.example.backend.enity.Videos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponse {

    private Post postData;
    private UserResponse user;
    private List<Videos> videos;
    private List<Images> images;
    private List<Extensions> extensions;
    private LocationResponse locationResponse;
}
