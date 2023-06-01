package com.ies.admin.entity;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name ="ies_plans")
public class PlansEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String startDate;
	private String endDate;
	private String planCategory;
	private Boolean active;
	
	private String  createdby;
	private LocalDate createdDate;
	private String updatedBy;
	private LocalDate updatedDate;

}
