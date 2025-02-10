package com.example.backend.dto.request.Search;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationRequest {

    private Long districtId;
    private Long provinceId;
    private Long wardId;
}
