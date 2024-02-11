package com.back.BuyAhead.Controller;

import com.back.BuyAhead.Domain.User;
import com.back.BuyAhead.Dto.Login.LoginRequestDto;
import com.back.BuyAhead.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String requestBody, HttpSession session) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            LoginRequestDto loginRequestDto = objectMapper.readValue(requestBody, LoginRequestDto.class);
            String token = userService.login(loginRequestDto);

            if (token != null) {
                // 로그인 성공
                return ResponseEntity.ok(token);
            } else {
                // 로그인 실패
                String responseMessage = "로그인 실패";
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
            }
        } catch (JsonProcessingException e) {
            // JSON 파싱 에러 처리
            String errorMessage = "JSON 파싱 에러: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (Exception e) {
            // 기타 예외 발생 시 403 Forbidden 반환
            String errorMessage = "서버 오류: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
        }
    }
}