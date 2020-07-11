package com.benz.web.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//AuthenticationResponse

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {

	private String token;
	private String type="Bearer";
	private long id;
	private String userName;
	private String active;
	private List<String> roles;
}
