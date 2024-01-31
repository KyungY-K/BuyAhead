package com.back.BuyAhead.Controller;

import com.back.BuyAhead.Dto.Image.ImageDetail;
import com.back.BuyAhead.Service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/upload", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ImageController {
    private final ImageUploadService imageUploadService;

    @PostMapping
    public ResponseEntity<ImageDetail> post(
            @RequestPart("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(imageUploadService.save(multipartFile));
    }
}