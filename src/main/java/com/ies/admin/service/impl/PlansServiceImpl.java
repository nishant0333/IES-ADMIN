package com.ies.admin.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ies.admin.constants.AppConstants;
import com.ies.admin.dto.Plans;
import com.ies.admin.entity.PlansEntity;
import com.ies.admin.exception.ResourceNotFountException;
import com.ies.admin.repo.PlansRepo;
import com.ies.admin.service.PlansService;

@Service
public class PlansServiceImpl implements PlansService {

	@Autowired
	private PlansRepo plansRepo;
	
	@Autowired
	private HttpSession session;

	@Override
	public String createPlans(Plans plan) {
		
		String logedinUser=(String)session.getAttribute("email");

		String name = plan.getName();

		Optional<PlansEntity> optional = plansRepo.findByName(name);

		if (optional.isEmpty()) {

			PlansEntity entity = new PlansEntity();

			BeanUtils.copyProperties(plan, entity);

			entity.setActive(true);
			
			
				entity.setCreatedby(logedinUser);
				entity.setCreatedDate(LocalDate.now());
				

			plansRepo.save(entity);

			return AppConstants.STR_PLAN_CREATED;

		} else {
			return AppConstants.STR_PLAN_EXIST;
		}

	}

	@Override
	public List<Plans> getAllPlans() {
		
		return plansRepo.findAll().stream()
				.filter(a -> a.getActive().equals(booleanMethod()))
				.map(e -> {
			
			Plans plan = new Plans();

			BeanUtils.copyProperties(e, plan);

			return plan;

		}).collect(Collectors.toList());

	}

	@Override
	public String editPlan(String name, Plans plan) {
		
		String logedinUser=(String)session.getAttribute("email");

		
		PlansEntity entity = plansRepo.findByName(name)
		.orElseThrow(()->new ResourceNotFountException(AppConstants.PLAN_EXCEPTION_MESSAGE));
		
		BeanUtils.copyProperties(plan, entity);
		
		entity.setUpdatedBy(logedinUser);
		entity.setUpdatedDate(LocalDate.now());
		
		plansRepo.save(entity);
		return AppConstants.STR_PLAN_UPDATED;
	}

	@Override
	public List<Plans> activeStatus(String name, Boolean status) {
		
		PlansEntity entity = plansRepo.findByName(name)
		.orElseThrow(()->new ResourceNotFountException(AppConstants.PLAN_EXCEPTION_MESSAGE));
		
		entity.setActive(status);
		
		plansRepo.save(entity);
		

		return plansRepo.findAll().stream()
				.filter(a -> a.getActive().equals(booleanMethod()))
				.map(e -> {
			
			Plans plan = new Plans();

			BeanUtils.copyProperties(e, plan);

			return plan;

		}).collect(Collectors.toList());

	}
	
	private Boolean booleanMethod() {
		return true;
	}

}
