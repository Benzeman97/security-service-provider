package com.benz.web.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.benz.web.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User,String>{

	Optional<User> findByUserName(String userName);
	
	boolean existsByUserName(String userName);
	
	@Query("select active from User where userName= :name")
	String findActive(@Param("name") String userName);
}
