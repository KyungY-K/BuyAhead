package com.back.BuyAhead;

import com.amazonaws.services.s3.AmazonS3;
import com.back.BuyAhead.Utils.MultipartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

@Component
public class AmazonS3ResourceStorage {
    private final String bucket;
    private final AmazonS3 amazonS3;

    @Autowired
    public AmazonS3ResourceStorage(AmazonS3 amazonS3) {
        this.bucket = "buyahead";
        this.amazonS3 = amazonS3;
    }

    public void store(String fullPath, MultipartFile multipartFile) {
        File file = new File(MultipartUtil.getLocalHomeDirectory(), fullPath);
        try {
            multipartFile.transferTo(file);
            amazonS3.putObject(new PutObjectRequest(bucket, fullPath, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            // 오류 발생 시 로그 출력
            e.printStackTrace();
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.");
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}