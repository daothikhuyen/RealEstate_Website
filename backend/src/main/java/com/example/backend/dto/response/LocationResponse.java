package com.example.backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationResponse {

    private Long provinceId;
    private Long districtId;
    private Long wardId;
    private String Title;

}
