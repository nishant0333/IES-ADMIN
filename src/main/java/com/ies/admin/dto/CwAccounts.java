package com.ies.admin.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CwAccounts {
	private String name;
	private String email;
	private Long mobile;
	private String gender;
	private String dob;
	private String ssn;
	
}
