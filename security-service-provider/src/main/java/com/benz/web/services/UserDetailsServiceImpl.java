package com.benz.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.benz.web.dao.UserDAO;
import com.benz.web.entity.User;
import com.benz.web.exception.DataNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	
	private UserDAO user_dao;
	
	@Autowired
    public UserDetailsServiceImpl(UserDAO use_dao) {
		
		this.user_dao=use_dao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=user_dao.findByUserName(username)
				.orElseThrow(()->new DataNotFoundException("User Not Found with username "+username));
		
		return  UserDetailsImpl.builder(user);
		
		
	}

}
