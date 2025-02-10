package com.example.backend.dto.response;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
     private Long id;
     private String username;
     private String password;
     private String phone;
     private String email;
     private String avatar;
     private Set<String> role;
}
