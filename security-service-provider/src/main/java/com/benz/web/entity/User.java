package com.benz.web.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.benz.web.config.Schema;

@Entity
@Table(name="USER56",schema = Schema.HR,uniqueConstraints = {
		@UniqueConstraint(columnNames = "USERNAME")
})

public class User {

	@Id
	@SequenceGenerator(name = "userId_SEQ",sequenceName = "userId_GEN",initialValue = 1005,allocationSize = 1)
	@GeneratedValue(generator = "userId_SEQ",strategy = GenerationType.SEQUENCE)
	@Column(name="USERID",nullable = false)
	private int userId;
	
	@Column(name="USERNAME",nullable = false)
	@Size(max = 70)
	private String userName;
	
	@Size(max = 50)
	@Column(name = "PASSWORD",nullable=false)
	private String password;
	
	@Column(name="ACTIVE",nullable=false)
	private String active;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="USERROLES",
	joinColumns = @JoinColumn(name="USERID"),
	inverseJoinColumns = @JoinColumn(name="ROLEID")
	)
	private Set<Role> roles=new HashSet<Role>();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	
}
