package com.example.backend.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) // bỏ các thông trả v bị null
public class ApiResponse<T> {
    @Builder.Default
    private int code = 1000;

    private String message;
    private T result;
}