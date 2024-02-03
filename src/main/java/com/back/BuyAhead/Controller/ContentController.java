package com.back.BuyAhead.Controller;

import com.back.BuyAhead.Domain.Content;
import com.back.BuyAhead.Dto.Content.ContentRequestDto;
import com.back.BuyAhead.Service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ContentController {

    @Autowired
    private ContentService contentService;

    @PostMapping("/content")
    public ResponseEntity<String> content(@RequestBody ContentRequestDto contentRequestDto) {
        try {
            Content content = contentService.content(contentRequestDto);
            if (content != null){
                String responseMessage = contentRequestDto.getContent();
                return ResponseEntity.ok(responseMessage);
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("작성 실패");
            }

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생: " + e.getMessage());
        }
    }
}
