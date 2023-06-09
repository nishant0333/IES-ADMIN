package com.ies.admin.entity;


import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "caseworker_accounts")
public class CwAccountsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String email;
	private Long mobile;
	private String gender;
	private String dob;
	private String ssn;
	private String pazzwod;
	private String status;
	private Boolean active;
	
	private String  createdby;
	private LocalDate createdDate;
	private String updatedBy;
	private LocalDate updatedDate;
	
//	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	private Role role;
	
	
	
}
