package com.example.backend.mapper;

import com.example.backend.dto.request.User.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.backend.dto.request.User.UserCreationRequest;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.enity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);// nhận đối tuưởng userrespone và chuyển thành user

    UserResponse toUserResponse(User user);

    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
