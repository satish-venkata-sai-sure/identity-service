package com.altimetrik.identityserver.contorller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.identityserver.exception.UserNotFoundException;
import com.altimetrik.identityserver.model.AuthRequest;
import com.altimetrik.identityserver.model.TokenResponse;
import com.altimetrik.identityserver.model.UserCredentialDto;
import com.altimetrik.identityserver.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@Valid @RequestBody UserCredentialDto user) {
    	System.out.println("Entered AuthController:addNewUser");
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> getToken(@Valid @RequestBody AuthRequest authRequest) throws UserNotFoundException {
    	System.out.println("Entered AuthController:getToken");
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
    	System.out.println("Entered AuthController:getToken:isAuthenticated = " + authenticate.isAuthenticated());

        if (authenticate.isAuthenticated()) {
        	 Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();
        	    List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        	TokenResponse tokenResponse = service.generateToken(authRequest.getUsername(),roles);
            return ResponseEntity.ok(tokenResponse);
        } else {
            throw new UserNotFoundException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}