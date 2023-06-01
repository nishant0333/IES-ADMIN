package com.ies.admin.service;

import java.util.List;


import com.ies.admin.dto.Plans;

public interface PlansService {

public String createPlans(Plans plan);
	
	public List<Plans> getAllPlans();
	
	public String editPlan(String name,Plans plan);
	
	public List<Plans> activeStatus(String name,Boolean status);
	
}
