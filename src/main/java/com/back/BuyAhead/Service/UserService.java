package com.back.BuyAhead.Service;

import com.back.BuyAhead.Dto.SignUp.SignUpRequestDto;
import com.back.BuyAhead.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.back.BuyAhead.Domain.User;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

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
}
