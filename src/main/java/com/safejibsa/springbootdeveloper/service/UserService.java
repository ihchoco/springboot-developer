package com.safejibsa.springbootdeveloper.service;

import com.safejibsa.springbootdeveloper.domain.User;
import com.safejibsa.springbootdeveloper.dto.AddUserRequest;
import com.safejibsa.springbootdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto){
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) //패스워드 암호화
                .build()).getId();
    }
    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }
}
