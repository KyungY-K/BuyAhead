package com.back.BuyAhead.Controller;

import com.back.BuyAhead.Domain.User;
import com.back.BuyAhead.Dto.SignUp.SignUpRequestDto;
import com.back.BuyAhead.Dto.Update.UpdateRequestDto;
import com.back.BuyAhead.Repository.UserRepository;
import com.back.BuyAhead.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
public class SignUpController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        try {
            //중복된 이메일
            if (userService.isEmailExists(signUpRequestDto.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 이메일입니다");
            }

            User user = userService.signUp(signUpRequestDto);
            if (user != null) {
                String responseMessage = signUpRequestDto.getEmail() + "\n" + signUpRequestDto.getName() + "\n"
                        + signUpRequestDto.getProfile_image() + "\n" + signUpRequestDto.getGreeting();
                return ResponseEntity.ok(responseMessage);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생: " + e.getMessage());
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateUserInformation(@PathVariable Long id, @RequestBody UpdateRequestDto updateRequestDto) {
        Optional<User> userOptional = userService.searchById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (updateRequestDto.getName() != null) {
                user.setName(updateRequestDto.getName());
            }
            if (updateRequestDto.getProfile_image() != null) {
                user.setProfile_image(updateRequestDto.getProfile_image());
            }
            if (updateRequestDto.getGreeting() != null) {
                user.setGreeting(updateRequestDto.getGreeting());
            }
            //비밀번호
            if (updateRequestDto.getPassword() != null) {
                user.setPassword(updateRequestDto.getPassword());
            }

            User updatedUser = userService.updateUserInformation(id, updateRequestDto);

            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                //업데이트 실패
                String errorMessage = "아이디 " + id + "번 사용자의 정보를 업데이트하는 데 실패했습니다.";
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
            }
        } else {
            //유저X
            String notFoundMessage = "회원 정보가 없습니다";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundMessage);
        }
    }
}