package com.ies.admin.utils;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;




@Component
public class EmailUtils {

	Logger logger= LoggerFactory.getLogger(EmailUtils.class);

	@Autowired
	private JavaMailSender mailSender;
	
	
	public Boolean sandMail(String subject ,String body ,String to)  {
		
		boolean isSent=false;
		
		try {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
		
		helper.setSubject(subject);
		helper.setText(body,true);
		helper.setTo(to);
		
		mailSender.send(mimeMessage);
		
		isSent=true;
		} catch (Exception e) {
			
			logger.error("Exception :: "+e.getMessage(), e);
	
}
		return isSent;
	}
	}
