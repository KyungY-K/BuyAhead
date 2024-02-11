package com.back.BuyAhead.Service;

import com.back.BuyAhead.Dto.Login.LoginRequestDto;
import com.back.BuyAhead.Dto.SignUp.SignUpRequestDto;
import com.back.BuyAhead.Dto.Update.UpdateRequestDto;
import com.back.BuyAhead.Repository.UserRepository;
import com.back.BuyAhead.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.back.BuyAhead.Domain.User;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
            // 이미 등록된 이메일이 있을 경우 처리
            System.out.print("중복된 이메일입니다");
        }

        User newUser = new User();
        newUser.setEmail(signUpRequestDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        newUser.setName(signUpRequestDto.getName());
        newUser.setProfile_image(signUpRequestDto.getProfile_image());
        newUser.setGreeting(signUpRequestDto.getGreeting());

        return userRepository.save(newUser);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
    }

    public Optional<User> searchById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUserInformation(Long id, UpdateRequestDto updateRequestDto) {
        Optional<User> userOptional = userRepository.findById(id);
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
                user.setPassword(passwordEncoder.encode(updateRequestDto.getPassword()));
            }

            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }

    public boolean friend(Long id) {
        return userRepository.existsById(id);
    }

    public String login(LoginRequestDto loginRequestDto) {
        Optional<User> byUserEmail = userRepository.findByEmail(loginRequestDto.getEmail());

        if (byUserEmail.isPresent()) {
            User userEntity = byUserEmail.get();

            if (passwordEncoder.matches(loginRequestDto.getPassword(), userEntity.getPassword())) {
                // 로그인 성공 시 토큰 생성하여 반환
                return jwtUtil.createToken(userEntity.getEmail());
            } else {
                // 비밀번호가 틀렸을 때
                return "비밀번호가 올바르지 않습니다.";
            }
        } else {
            // 이메일이 존재하지 않을 때
            return "존재하지 않는 이메일입니다.";
        }
    }
}