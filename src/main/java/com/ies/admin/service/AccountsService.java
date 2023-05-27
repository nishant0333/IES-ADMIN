package com.ies.admin.service;

import java.util.List;

import com.ies.admin.dto.CwAccounts;

public interface AccountsService {

	public String createAccount(CwAccounts account);
	
	public List<CwAccounts> getAllAccounts();
	
	public String editAccount(String email,CwAccounts accounts);
	
	public List<CwAccounts> activeStatus(String email,Boolean status);
	
	
	
}
