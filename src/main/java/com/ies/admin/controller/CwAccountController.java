package com.ies.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ies.admin.dto.CwAccounts;
import com.ies.admin.service.AccountsService;

@RestController
@RequestMapping("/CwAccount")
public class CwAccountController {
	
	@Autowired
	private AccountsService accountsService;
	
	@PostMapping("/create")
	public ResponseEntity<String> createAccount(@RequestBody CwAccounts account){
		
		return new ResponseEntity<String>(accountsService.createAccount(account),HttpStatus.CREATED);
		
		
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<CwAccounts>> getAllAccounts(){

		return new ResponseEntity<List<CwAccounts>>(accountsService.getAllAccounts(),HttpStatus.OK);
	}
	
	@PutMapping("/edit/{email}")
	public ResponseEntity<String> editAccount(@PathVariable String email,@RequestBody  CwAccounts account){
		
		return new ResponseEntity<String>(accountsService.editAccount(email, account),HttpStatus.CREATED);
	}
	
	
	@GetMapping("/activeStatus/{email}/{status}")
	public ResponseEntity<List<CwAccounts>> activeStatus(@PathVariable String email, @PathVariable Boolean status){
		
		return new ResponseEntity<List<CwAccounts>>(accountsService.activeStatus(email, status),HttpStatus.OK);
	}

	
	
}
