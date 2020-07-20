package com.benz.web.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//AuthenticationResponse

@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {

	private String token;
	private String type="Bearer";
	private String userName;
	private boolean active;
	private List<String> roles;

	public JwtResponse(String token, String userName,boolean active, List<String> roles) {
		this.token = token;
		this.userName = userName;
		this.active = active;
		this.roles = roles;
	}
}
