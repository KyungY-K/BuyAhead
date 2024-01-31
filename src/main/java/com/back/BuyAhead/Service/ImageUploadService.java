package com.back.BuyAhead.Service;

import com.back.BuyAhead.Dto.Image.ImageDetail;
import com.back.BuyAhead.AmazonS3ResourceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    public ImageDetail save(MultipartFile multipartFile) {
        ImageDetail imageDetail = ImageDetail.multipartOf(multipartFile);
        amazonS3ResourceStorage.store(imageDetail.getPath(), multipartFile);
        return imageDetail;
    }
}