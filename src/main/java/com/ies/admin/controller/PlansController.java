package com.ies.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ies.admin.dto.Plans;
import com.ies.admin.service.PlansService;

@RestController
@RequestMapping("/plans")
public class PlansController {
	
	@Autowired
	private PlansService plansService;
	
	@GetMapping("/activeStatus/{name}/{status}")
	public ResponseEntity<List<Plans>> activeStatus(@PathVariable String name,@PathVariable Boolean status){
		
		
		return new ResponseEntity<>(plansService.activeStatus(name, status),HttpStatus.OK);
	}
	
	@PutMapping("/edit/{name}")
	public ResponseEntity<String> editPlan(@PathVariable String name,@RequestBody Plans plan){
		
		return new ResponseEntity<>(plansService.editPlan(name, plan),HttpStatus.CREATED);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<Plans>> getAllPlans(){
		
		return new ResponseEntity<>(plansService.getAllPlans(),HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createPlan(@RequestBody Plans plan) {
		
		return new ResponseEntity<>(plansService.createPlans(plan),HttpStatus.CREATED);
	}
	
	

}
