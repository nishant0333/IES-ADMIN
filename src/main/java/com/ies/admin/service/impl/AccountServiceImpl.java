package com.ies.admin.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ies.admin.constants.AppConstants;
import com.ies.admin.dto.CwAccounts;
import com.ies.admin.entity.CwAccountsEntity;
import com.ies.admin.exception.ResourceNotFountException;
import com.ies.admin.props.AppProperties;
import com.ies.admin.repo.AccountsRepo;
import com.ies.admin.service.AccountsService;
import com.ies.admin.utils.EmailUtils;
import com.ies.admin.utils.PwdUtils;

@Service
public class AccountServiceImpl implements AccountsService {

	@Autowired
	private AccountsRepo accountsRepo;

	@Autowired
	private PwdUtils pwdUtils;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private AppProperties props;

	@Override
	public String createAccount(CwAccounts account) {

		String email = account.getEmail();

		 Optional<CwAccountsEntity> optional = accountsRepo.findByEmail(email);

		if (optional.isEmpty()) {

			CwAccountsEntity cwEntity = new CwAccountsEntity();

			BeanUtils.copyProperties(account, cwEntity);

			cwEntity.setActive(true);
			cwEntity.setStatus(AppConstants.STR_LOCKED);

			String tempPazzwod = pwdUtils.generateRandompwdUUID();

			cwEntity.setPazzwod(tempPazzwod);

			accountsRepo.save(cwEntity);
			
			
			// sand email to unlock the account
			String to = account.getEmail();
			String subject = props.getMessages().get(AppConstants.EMAIL_SUBJECT_KEY);
			StringBuffer body = new StringBuffer("");
			body.append(props.getMessages().get(AppConstants.EMAIL_BODY_MESSAGE_KEY));
			body.append(AppConstants.EMAIL_USER_ID + to);
			body.append(AppConstants.EMAIL_BR_TAG);
			body.append(AppConstants.TEMPORARY_PAZZWORD + tempPazzwod);
			
			emailUtils.sandMail(subject, body.toString(), to);


			return AppConstants.STR_ACCOUNT_CREATED;

		} else {
			return props.getMessages().get(AppConstants.EMAIL_EXIST_KEY);
		}

	}

	@Override
	public List<CwAccounts> getAllAccounts() {
		List<CwAccountsEntity> findAll = accountsRepo.findAll();

		List<CwAccounts> cwlist = findAll.stream().filter(a->a.getActive()==true).map(e -> {

			CwAccounts cwAccount = new CwAccounts();

			BeanUtils.copyProperties(e, cwAccount);

			return cwAccount;

		}).collect(Collectors.toList());

		return cwlist;
	}

	@Override
	public String editAccount(String email, CwAccounts accounts) {
		
		 CwAccountsEntity entity = accountsRepo.findByEmail(email)
		.orElseThrow(() -> new ResourceNotFountException(AppConstants.CW_ACCOUNT_EXCEPTION_MESSAGE));
		 
		 BeanUtils.copyProperties(accounts, entity);
		 
		 accountsRepo.save(entity);
		
		return AppConstants.STR_ACCOUNT_UPDATED;
	}

	@Override
	public List<CwAccounts> activeStatus(String email, Boolean status) {
		
		CwAccountsEntity entity = accountsRepo.findByEmail(email)
				.orElseThrow(()-> new ResourceNotFountException(AppConstants.CW_ACCOUNT_EXCEPTION_MESSAGE));
		
		entity.setActive(status);
		
		accountsRepo.save(entity);
		
		List<CwAccountsEntity> findAll = accountsRepo.findAll();
		
		List<CwAccounts> cwlist = findAll.stream().filter(a->a.getActive()==true).map(e -> {

			CwAccounts cwAccount = new CwAccounts();

			BeanUtils.copyProperties(e, cwAccount);

			return cwAccount;

		}).collect(Collectors.toList());

		return cwlist;
		
		
	}

}
