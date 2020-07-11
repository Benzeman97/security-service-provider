package com.benz.web.controllers;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	 private AuthenticationManager authManager;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws Exception
	{
		try {
			
		 Authentication authentication=authManager.authenticate(loginRequest.getUserName(),loginRequest.getPassword());
		 
		}catch(BadCredentialsException bx)
		{
			throw new Exception("Email or Password in invalid");
		}
		 
		 
	}
}
