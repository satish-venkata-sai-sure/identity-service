package com.altimetrik.identityserver.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.altimetrik.identityserver.entity.UserCredential;
import com.altimetrik.identityserver.repository.UserCredentialRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> credential = repository.findByName(username);
        if(credential.isPresent()) {
        	System.out.println("user is present");
        }else {
        	System.out.println("user is NOT present");
        	throw new UsernameNotFoundException("user is NOT present with the name :"+ username);
        }
        return credential.map(CustomUserDetails::new).get();
    }
}