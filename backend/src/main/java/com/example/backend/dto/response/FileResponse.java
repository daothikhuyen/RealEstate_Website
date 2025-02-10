package com.example.backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileResponse {

     Resource resource;
     MediaType mediaType;
     String file;
}
