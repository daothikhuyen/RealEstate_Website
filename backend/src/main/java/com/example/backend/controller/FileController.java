package com.example.backend.controller;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.FileResponse;
import com.example.backend.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/storage")
public class FileController {

    @Autowired
    StorageService storageService;

    @PostMapping("/fileSystem")
    public ApiResponse<FileResponse> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                      RedirectAttributes redirectAttributes) throws IOException {

        var url  = storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return ApiResponse.<FileResponse>builder()
                .result(url)
                .build();
    }


    @GetMapping("/uploads/{year}/{month}/{filename}")
    public ResponseEntity<Resource> loadFile(@PathVariable String year, @PathVariable String month, @PathVariable String filename) throws MalformedURLException {

        FileResponse fileResponse = storageService.loadFile(year,month,filename);

        return ResponseEntity.ok()
                .contentType(fileResponse.getMediaType()) // Tùy chỉnh loại ảnh nếu cần
                .body(fileResponse.getResource());

    }


}
