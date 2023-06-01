package com.ies.admin.service;

import java.util.List;

import com.ies.admin.dto.CwAccounts;
import com.ies.admin.dto.DashboardResponce;

public interface AccountsService {

	public String createAccount(CwAccounts account);
	
	public List<CwAccounts> getAllAccounts();
	
	public String editAccount(String email,CwAccounts accounts);
	
	public List<CwAccounts> activeStatus(String email,Boolean status);
	
	
	public String unlockAccount(String email,String tempPazzword,String newPazzword);
	
	public String login(String email,String pazzword);
	
	public String forgotPazzword(String email);
	
	public DashboardResponce dashboard();
	
	
}
