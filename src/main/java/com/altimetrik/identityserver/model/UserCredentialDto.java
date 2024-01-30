package com.altimetrik.identityserver.model;

import java.util.List;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialDto {

    @Digits(integer = 10, fraction = 0, message = "Please enter a valid integer value")
    private int id;
    
    @NotNull(message = "name shouldn't be null")
    private String name;
    
    @Email
    private String email;
    
    
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
    
    @NotNull(message = "role shouldn't be null")
    private List<String> roles;
}

