package com.example.backend.service;

import com.example.backend.dto.response.FileResponse;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class StorageService {

//    @Value("${file.upload-dir}")
//    private String uploadDir;

    public FileResponse store(MultipartFile file) throws IOException {

        if(file.isEmpty()){
            throw new AppException(ErrorCode.FILE_NOT_EXISTED);
        }
        String currentWorkingDir = System.getProperty("user.dir");
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        Path pathFull = Paths.get(currentWorkingDir,"src","main","resources","static","storage","uploads", datePath);


        Files.createDirectories(pathFull);

        // Đường dẫn đầy đủ của tệp
        String filePath = pathFull.resolve(file.getOriginalFilename()).toString();

        if (Files.exists(Path.of(filePath))) {
            // Nếu tệp đã tồn tại, trả về URL của tệp hiện tại
            return FileResponse.builder()
                    .file("/storage/uploads/" + datePath + "/" + file.getOriginalFilename())
                    .build();
        }

        file.transferTo(new File(filePath));

        String fileUrl = "/storage/uploads/" + datePath +"/"+ file.getOriginalFilename();

        return FileResponse.builder()
                .file(fileUrl)
                .build();

    }

    //    Lấy đuôi file
    private String getFileExtension(String file) {

        int lastIndexOf = file.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return file.substring(lastIndexOf);
    }

    public FileResponse loadFile(String year, String month, String filename) throws MalformedURLException {

        String currentWorkingDir = System.getProperty("user.dir");

        Path file = Paths.get(currentWorkingDir,"src","main","resources","static","storage", "uploads", year, month, filename);

        Resource resource = new UrlResource(file.toUri());

        if (!resource.exists()) {
            throw new AppException(ErrorCode.FILE_NOT_EXISTED);
        }

        String fileExtension  = getFileExtension(filename);
        MediaType mediaType;

        switch (fileExtension){
            case ".mp4":
                mediaType = MediaType.parseMediaType("video/mp4");
                break;
            case ".jpg":
            case ".jpeg":
            case ".png":
                mediaType = MediaType.IMAGE_JPEG; // có thể đổi sang loại MIME ảnh khác nếu cần
                break;
            default:
                throw new AppException(ErrorCode.FILE_NOT_EXISTED);
        }

        return new FileResponse(resource, mediaType, "");
    }
}
