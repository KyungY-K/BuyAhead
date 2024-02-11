package com.back.BuyAhead.Security;

import com.back.BuyAhead.Domain.User;
import com.back.BuyAhead.Utils.JwtUtil;
import com.back.BuyAhead.Dto.Login.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger("로그인 및 JWT 생성");
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.setFilterProcessesUrl("/api/user/login");
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = (LoginRequestDto)(new ObjectMapper()).readValue(request.getInputStream(), LoginRequestDto.class);
            return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword(), (Collection)null));
        } catch (IOException var4) {
            log.error(var4.getMessage());
            throw new RuntimeException(var4.getMessage());
        }
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl)authResult.getPrincipal()).getUsername();
        String token = this.jwtUtil.createToken(username);
        response.addHeader("Authorization", token);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}