package com.altimetrik.identityserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altimetrik.identityserver.entity.UserCredential;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> {
    Optional<UserCredential> findByName(String username);
}