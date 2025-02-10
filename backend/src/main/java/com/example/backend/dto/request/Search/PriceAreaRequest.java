package com.example.backend.dto.request.Search;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceAreaRequest {
    private String name;
    private double on;
    private double under;
}
