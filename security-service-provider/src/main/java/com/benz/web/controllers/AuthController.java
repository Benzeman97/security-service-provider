package com.benz.web.controllers;

import com.benz.web.config.ERole;
import com.benz.web.dao.RoleDAO;
import com.benz.web.dao.UserDAO;
import com.benz.web.entity.Role;
import com.benz.web.entity.User;
import com.benz.web.models.JwtResponse;
import com.benz.web.models.LoginRequest;
import com.benz.web.models.MessageResponse;
import com.benz.web.models.SignUpRequest;
import com.benz.web.security.JwtUtil;
import com.benz.web.services.UserDetailsImpl;
import com.benz.web.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

	 @Autowired
	 private UserDAO user_dao;

	 @Autowired
	 private RoleDAO role_dao;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws Exception
	{
		try {
			
		 authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));

		 final UserDetailsImpl userDetails=(UserDetailsImpl) userDetailsService.loadUserByUsername(loginRequest.getUserName());
		 String jwt=jwtUtil.generateToken(userDetails);

		 List<String> roles=userDetails.getAuthorities().stream().map(item-> item.getAuthority())
				 .collect(Collectors.toList());

		 return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(),userDetails.isEnabled(),roles));


		}catch(BadCredentialsException bx)
		{
			throw new Exception("Email or Password in invalid");
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest)
	{
		if(user_dao.existsByUserName(signUpRequest.getUserName()))
			return ResponseEntity.badRequest().body(new MessageResponse("Error userName is already taken!"));

		User user=new User();
		user.setUserName(signUpRequest.getUserName());
		user.setPassword(signUpRequest.getPassword());
		user.setActive("Y");

		Set<String> strRoles=signUpRequest.getRoles();
		Set<Role> roles=new HashSet<>();

		if(strRoles==null)
		{
			Role userRole=role_dao.findByName(ERole.ROlE_USER)
					.orElseThrow(()->new RuntimeException("Error : Role is not found!"));
			roles.add(userRole);
		}else {
			strRoles.forEach(role ->{
				switch(role.toLowerCase())
				{
					case "admin":
						Role adminRole= role_dao.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(()->new RuntimeException("Error : Role is not found!"));
						roles.add(adminRole);break;
					case "mod":
						Role modRole=role_dao.findByName(ERole.ROLE_MODERATOR)
								.orElseThrow(()->new RuntimeException("Error : Role is not found!"));
						roles.add(modRole);break;
					default:
						Role userRole=role_dao.findByName(ERole.ROlE_USER)
								.orElseThrow(()->new RuntimeException("Error : Role is not found!"));
						roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		user_dao.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully"));
	}
}
