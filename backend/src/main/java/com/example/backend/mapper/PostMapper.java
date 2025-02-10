package com.example.backend.mapper;

import com.example.backend.dto.request.PostRequest;
import com.example.backend.dto.request.Search.LocationRequest;
import com.example.backend.enity.Images;
import com.example.backend.enity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toLocationPost(LocationRequest request);

    Post toPost(Post request);

    void updatePost(@MappingTarget Post post, PostRequest postRequest);

    @Mapping(target = "id", ignore = true)
    void updateImage(@MappingTarget Images images, PostRequest postRequest);
}
