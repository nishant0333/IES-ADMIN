package com.ies.admin.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;


@Component
public class PwdUtils {

public String generateRandompwdUUID() {
		
		String string = UUID.randomUUID().toString();
		
		return string;
		
	}
	
}
