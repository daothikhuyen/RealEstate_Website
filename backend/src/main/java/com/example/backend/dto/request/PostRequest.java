package com.example.backend.dto.request;

import com.example.backend.dto.response.UserResponse;
import com.example.backend.enity.Extensions;
import com.example.backend.enity.Images;
import com.example.backend.enity.Post;
import com.example.backend.enity.Videos;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequest {

    private Post postData;
    private UserResponse user;
    private List<Videos> videos;
    private List<Images> images;
    private List<Extensions> extensions;

}
