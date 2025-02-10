package com.example.backend.dto.request.User;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationRequest {

    private String username;
    private String password;
    private String email;
}
