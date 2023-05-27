package com.ies.admin.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ies.admin.dto.CwAccounts;
import com.ies.admin.entity.CwAccountsEntity;
import java.util.List;


public interface AccountsRepo extends JpaRepository<CwAccountsEntity, Integer>{

	
	public Optional<CwAccountsEntity>  findByEmail(String email);
}
