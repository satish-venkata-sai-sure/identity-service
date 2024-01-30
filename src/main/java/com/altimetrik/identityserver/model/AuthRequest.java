package com.altimetrik.identityserver.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

	@NotNull(message = "username shouldn't be null")
    private String username;
	
	@NotNull(message = "password shouldn't be null")
    private String password;

}
