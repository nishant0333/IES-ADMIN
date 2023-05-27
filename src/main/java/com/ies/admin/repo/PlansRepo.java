package com.ies.admin.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ies.admin.entity.PlansEntity;
import java.util.List;


public interface PlansRepo extends JpaRepository<PlansEntity, Integer> {

	public Optional<PlansEntity>  findByName(String name);
	
}
