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
import com.ies.admin.dto.CwAccounts;
import com.ies.admin.dto.DashboardResponce;
import com.ies.admin.entity.CwAccountsEntity;
import com.ies.admin.entity.PlansEntity;
import com.ies.admin.exception.ResourceNotFountException;
import com.ies.admin.props.AppProperties;
import com.ies.admin.repo.AccountsRepo;
import com.ies.admin.repo.PlansRepo;
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
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private PlansRepo palnRepo;

	@Override
	public String createAccount(CwAccounts account) {


		String logedinUser=(String)session.getAttribute("email");
		
		 Optional<CwAccountsEntity> optional = accountsRepo.findByEmail(account.getEmail());

		if (optional.isEmpty()) {

			CwAccountsEntity cwEntity = new CwAccountsEntity();

			BeanUtils.copyProperties(account, cwEntity);

			cwEntity.setActive(true);

			cwEntity.setStatus(AppConstants.STR_LOCKED);

			String tempPazzwod = pwdUtils.generateRandompwdUUID();

			cwEntity.setPazzwod(tempPazzwod);
			
			
			cwEntity.setCreatedby(logedinUser);
			cwEntity.setCreatedDate(LocalDate.now());
			
			

			accountsRepo.save(cwEntity);
			
			
			// sand email to unlock the account
			String to = account.getEmail();
			String subject = props.getMessages().get(AppConstants.EMAIL_SUBJECT_KEY);
			StringBuilder body = new StringBuilder("");
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
		

		return accountsRepo.findAll().stream()
				.filter(a->a.getActive().equals(booleanMethod()))
				.map(e -> {

			CwAccounts cwAccount = new CwAccounts();

			BeanUtils.copyProperties(e, cwAccount);

			return cwAccount;

		}).collect(Collectors.toList());

		
	}

	@Override
	public String editAccount(String email, CwAccounts accounts) {
		
		String logedinUser=(String)session.getAttribute("email");
		
		 CwAccountsEntity entity = accountsRepo.findByEmail(email)
		.orElseThrow(() -> new ResourceNotFountException(AppConstants.CW_ACCOUNT_EXCEPTION_MESSAGE));
		 
		 BeanUtils.copyProperties(accounts, entity);
		 
		
		 entity.setUpdatedBy(logedinUser);
		 entity.setUpdatedDate(LocalDate.now());
			
		 
		 accountsRepo.save(entity);
		
		return AppConstants.STR_ACCOUNT_UPDATED;
	}

	@Override
	public List<CwAccounts> activeStatus(String email, Boolean status) {
		
		CwAccountsEntity entity = accountsRepo.findByEmail(email)
				.orElseThrow(()-> new ResourceNotFountException(AppConstants.CW_ACCOUNT_EXCEPTION_MESSAGE));
		
		entity.setActive(status);
		
		accountsRepo.save(entity);
		
		
		
		return accountsRepo.findAll().stream()
				.filter(a->a.getActive().equals(booleanMethod()))
				.map(e -> {

			CwAccounts cwAccount = new CwAccounts();

			BeanUtils.copyProperties(e, cwAccount);

			return cwAccount;

		}).collect(Collectors.toList());

	}
	
	private Boolean booleanMethod() {
		return true;
	}

	@Override
	public String unlockAccount(String email, String tempPazzword, String newPazzword) {
		
		CwAccountsEntity entity = accountsRepo.findByEmail(email)
				.orElseThrow(()-> new ResourceNotFountException(AppConstants.CW_ACCOUNT_EXCEPTION_MESSAGE));
		
		if (entity.getStatus().equals(AppConstants.STR_LOCKED) ) {
			
			if (entity.getPazzwod().equals(tempPazzword)) {
				
				entity.setPazzwod(newPazzword);
				entity.setStatus(AppConstants.STR_UNLOCKED);
				
				accountsRepo.save(entity);
				
				return AppConstants.STR_UNLOCKED;
			}else {
				return AppConstants.STR_INVALID_CREDENTIALS;
			}
			
				
		}else {
			return AppConstants.STR_UNLOCKED;
		}
		
		
	}

	@Override
	public String login(String email, String pazzword) {
		
		CwAccountsEntity entity = accountsRepo.findByEmail(email)
		.orElseThrow(()->new ResourceNotFountException(AppConstants.CW_ACCOUNT_EXCEPTION_MESSAGE));
		
		if (entity.getStatus().equals(AppConstants.STR_UNLOCKED)) {
			
			
			if (entity.getPazzwod().equals(pazzword)) {
				
				session.setAttribute("email", entity.getEmail());
				
				return AppConstants.STR_SUCCESS;
				
			}else {
				return AppConstants.STR_INVALID_CREDENTIALS;
			}
			
			
		}else {
			return AppConstants.STR_LOCKED;
		}
		
	}

	@Override
	public String forgotPazzword(String email) {
		

		CwAccountsEntity entity = accountsRepo.findByEmail(email)
		.orElseThrow(()->new ResourceNotFountException(AppConstants.CW_ACCOUNT_EXCEPTION_MESSAGE));
		
		String body="<h2>"+"password :"+entity.getPazzwod()+"</h2>";
		
		
		emailUtils.sandMail(AppConstants.FORGOT_PAZZWORD_SUBJECT_KEY, body, entity.getEmail());
		
		
		return AppConstants.FORGOT_PAZZWORD_RESPONSE+entity.getEmail();
	}

	@Override
	public DashboardResponce dashboard() {
	

		 DashboardResponce responce = DashboardResponce.builder()
		 .noOfplans(palnRepo.findAll().size())
		 .citizensApproved(333)
		 .citizensDenied(555)
		 .benefitsGiven(777).build();
		 
     return responce;
	}

}
