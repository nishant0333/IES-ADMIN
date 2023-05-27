package com.ies.admin.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Override
	public String createPlans(Plans plan) {

		String name = plan.getName();

		Optional<PlansEntity> optional = plansRepo.findByName(name);

		if (optional.isEmpty()) {

			PlansEntity entity = new PlansEntity();

			BeanUtils.copyProperties(plan, entity);

			entity.setActive(true);

			plansRepo.save(entity);

			return AppConstants.STR_PLAN_CREATED;

		} else {
			return AppConstants.STR_PLAN_EXIST;
		}

	}

	@Override
	public List<Plans> getAllPlans() {
		List<PlansEntity> findAll = plansRepo.findAll();

		List<Plans> activePlans = findAll.stream().filter(a -> a.getActive() == true).map(e -> {
			
			Plans plan = new Plans();

			BeanUtils.copyProperties(e, plan);

			return plan;

		}).collect(Collectors.toList());

		return activePlans;
	}

	@Override
	public String editPlan(String name, Plans Plan) {
		PlansEntity entity = plansRepo.findByName(name)
		.orElseThrow(()->new ResourceNotFountException(AppConstants.PLAN_EXCEPTION_MESSAGE));
		
		BeanUtils.copyProperties(Plan, entity);
		
		plansRepo.save(entity);
		return AppConstants.STR_PLAN_UPDATED;
	}

	@Override
	public List<Plans> activeStatus(String name, Boolean status) {
		
		PlansEntity entity = plansRepo.findByName(name)
		.orElseThrow(()->new ResourceNotFountException(AppConstants.PLAN_EXCEPTION_MESSAGE));
		
		entity.setActive(status);
		
		plansRepo.save(entity);
		
		List<PlansEntity> findAll = plansRepo.findAll();

		List<Plans> activePlans = findAll.stream()
				.filter(a -> a.getActive() == true)
				.map(e -> {
			
			Plans plan = new Plans();

			BeanUtils.copyProperties(e, plan);

			return plan;

		}).collect(Collectors.toList());

		return activePlans;	
	}

}
