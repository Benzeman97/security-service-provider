package com.benz.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.benz.web.config.ERole;
import com.benz.web.config.Schema;

@Entity
@Table(name = "ROLES",schema = Schema.HR)
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLEID")
	private int id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "NAME",nullable=false)
	@Size(max = 30)
	private ERole name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
	
	
}
