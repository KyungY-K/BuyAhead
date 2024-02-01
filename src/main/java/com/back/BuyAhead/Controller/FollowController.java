package com.back.BuyAhead.Controller;

import com.back.BuyAhead.Domain.User;
import com.back.BuyAhead.Dto.Follow.FollowRequestDto;
import com.back.BuyAhead.Service.FollowService;
import com.back.BuyAhead.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class FollowController {

    @Autowired
    private FollowService followService;
    @Autowired
    private UserService userService;

    @PostMapping("/follow/{id}")
    public ResponseEntity<String> follow(@PathVariable Long id, @RequestBody FollowRequestDto followRequestDto) {
        Optional<User> userOptional = userService.searchById(id);
        try {
            if (userOptional.isPresent()) {
                followService.addFollow(followRequestDto);
                return ResponseEntity.ok("팔로우 추가 성공");
            } else {
                String notFoundMessage = "회원 정보가 없습니다";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}