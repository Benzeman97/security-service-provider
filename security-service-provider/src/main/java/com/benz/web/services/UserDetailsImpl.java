package com.benz.web.services;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.benz.web.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class UserDetailsImpl implements UserDetails{
	
	private String userName;
	
	@JsonIgnore
	private String password;
	
	private boolean active;
	
	private List<GrantedAuthority> authorities;
	
	
	public UserDetailsImpl(String userName, String password,String active, List<GrantedAuthority> authorities) {
		this.userName = userName;
		this.password = password;
		if(active.equalsIgnoreCase("y"))
		this.active = true;
		else
			this.active=false;
		this.authorities = authorities;
	}
	
	
	public static UserDetailsImpl builder(User user) {
		  
		List<GrantedAuthority> authorities=user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName().name())
				).collect(Collectors.toList());
		
		return new UserDetailsImpl(user.getUserName(),user.getPassword(),user.getActive(),authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {

		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

}
