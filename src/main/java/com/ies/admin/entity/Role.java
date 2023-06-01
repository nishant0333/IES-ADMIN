package com.ies.admin.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "user_roles")
public class Role {

	private Integer roleId;
	private String roleName;
	
}
