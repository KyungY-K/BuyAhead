package com.back.BuyAhead.Controller;

import com.back.BuyAhead.Domain.User;
import com.back.BuyAhead.Dto.SignUp.SignUpRequestDto;
import com.back.BuyAhead.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        User user = userService.signUp(signUpRequestDto);
        if (user != null) {
            String responseMessage = signUpRequestDto.getEmail() + "\n" + signUpRequestDto.getName() + "\n"
                    + signUpRequestDto.getProfile_image() + "\n" + signUpRequestDto.getGreeting();
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed");
        }
    }
}