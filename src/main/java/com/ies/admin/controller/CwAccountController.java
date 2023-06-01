package com.ies.admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ies.admin.constants.AppConstants;
import com.ies.admin.dto.CwAccounts;
import com.ies.admin.dto.DashboardResponce;
import com.ies.admin.service.AccountsService;
import com.ies.admin.service.PlansService;

@RestController
@RequestMapping("/CwAccount")
public class CwAccountController {
	
	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private PlansService plansService;
	
	@Autowired
	private HttpSession session;
	
	@PostMapping("/create")
	public ResponseEntity<String> createAccount(@RequestBody CwAccounts account){
		
		return new ResponseEntity<>(accountsService.createAccount(account),HttpStatus.CREATED);
		
		
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<CwAccounts>> getAllAccounts(){

		return new ResponseEntity<>(accountsService.getAllAccounts(),HttpStatus.OK);
	}
	
	@PutMapping("/edit/{email}")
	public ResponseEntity<String> editAccount(@PathVariable String email,@RequestBody  CwAccounts account){
		
		return new ResponseEntity<>(accountsService.editAccount(email, account),HttpStatus.CREATED);
	}
	
	
	@GetMapping("/activeStatus/{email}/{status}")
	public ResponseEntity<List<CwAccounts>> activeStatus(@PathVariable String email, @PathVariable Boolean status){
		
		return new ResponseEntity<>(accountsService.activeStatus(email, status),HttpStatus.OK);
	}
	
	
	@GetMapping("/unlock")
	public ResponseEntity<String> unlockAccount(
			@RequestParam String email,
			@RequestParam String tempPazzword,
			@RequestParam String newPazzword
			){
		
		return new ResponseEntity<>(accountsService.unlockAccount(email, tempPazzword, newPazzword),HttpStatus.OK);
	}
	
	@GetMapping("ieslogin/{email}/{pazzword}")
	public ResponseEntity<String> login(@PathVariable String email,@PathVariable String pazzword){
		
		return new ResponseEntity<>(accountsService.login(email, pazzword),HttpStatus.OK);
	}
	
	
	@GetMapping("/forgot/{email}")
	public ResponseEntity<String> forgotPazzword(@PathVariable String email){
		
		return ResponseEntity.status(HttpStatus.OK).body(accountsService.forgotPazzword(email));
		
	}
	@GetMapping("/dashboard")
	public ResponseEntity<DashboardResponce> dashboard(){
		
		return ResponseEntity.status(HttpStatus.OK).body(accountsService.dashboard());
	}

	@GetMapping("/ieslogout")
	public ResponseEntity<String> logout(){
		
		session.invalidate();
		
		return ResponseEntity.status(HttpStatus.OK).body(AppConstants.STR_LOGOUT);
		
	}
	
}
