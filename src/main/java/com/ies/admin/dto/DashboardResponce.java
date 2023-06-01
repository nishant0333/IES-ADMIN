package com.ies.admin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponce {

	private Integer noOfplans;
	private Integer citizensApproved;
	private Integer citizensDenied;
	private Integer benefitsGiven;
	
}
