package com.example.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RattingResponse {

    private String status;
    private String message;
}
