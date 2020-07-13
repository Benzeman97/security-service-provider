package com.benz.web.controllers;

import com.benz.web.models.JwtResponse;
import com.benz.web.security.JwtUtil;
import com.benz.web.services.UserDetailsImpl;
import com.benz.web.services.UserDetailsServiceImpl;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	 @Autowired
	 private AuthenticationManager authManager;

	 @Autowired
	 private JwtUtil jwtUtil;

	 @Autowired
	 private UserDetailsServiceImpl userDetailsService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws Exception
	{
		try {
			
		 Authentication authentication=authManager.authenticate(loginRequest.getUserName(),loginRequest.getPassword());

		 final UserDetailsImpl userDetails=userDetailsService.loadUserByUsername(loginRequest.getUserName());
		 String jwt=jwtUtil.generateToken(userDetails);

		 List<String> roles=userDetails.getAuthorities().stream().map(item-> item.getAuthority())
				 .collect(Collectors.toList());

		 return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(),userDetails.isEnabled(),roles));


		}catch(BadCredentialsException bx)
		{
			throw new Exception("Email or Password in invalid");
		}
	}

	//signup
}
