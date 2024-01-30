package com.altimetrik.identityserver.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.altimetrik.identityserver.entity.UserCredential;
import com.altimetrik.identityserver.model.TokenResponse;
import com.altimetrik.identityserver.model.UserCredentialDto;
import com.altimetrik.identityserver.repository.UserCredentialRepository;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredentialDto userDto) {
    	userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
    	UserCredential userCredential = convertToEntity(userDto);
        repository.save(userCredential);
        System.out.println("successfully stored in database");

        return "User successfully register in the system";
    }

    public TokenResponse generateToken(String username, List<String> roles) {
        System.out.println("AuthService:generateToken");
        String token = jwtService.generateToken(username, roles);
        TokenResponse tokenResponse = new TokenResponse(token);
        return tokenResponse;
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    private UserCredentialDto convertToDTO(UserCredential categoryEntity) {
        return modelMapper.map(categoryEntity, UserCredentialDto.class);
    }

    private UserCredential convertToEntity(UserCredentialDto categoryDTO) {
        return modelMapper.map(categoryDTO, UserCredential.class);
    }

}