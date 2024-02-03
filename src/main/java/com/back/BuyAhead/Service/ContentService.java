package com.back.BuyAhead.Service;

import com.back.BuyAhead.Domain.Content;
import com.back.BuyAhead.Dto.Content.ContentRequestDto;
import com.back.BuyAhead.Repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public Content content(ContentRequestDto contentRequestDto) {
        Content newContent = new Content();
        newContent.setContent(contentRequestDto.getContent());

        return contentRepository.save(newContent);
    }
}
