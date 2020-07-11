package com.benz.web.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.benz.web.config.ERole;
import com.benz.web.entity.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role,Integer>{

	 Optional<Role> findByName(ERole name);
}
