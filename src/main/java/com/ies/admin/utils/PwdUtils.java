package com.ies.admin.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;


@Component
public class PwdUtils {

public String generateRandompwdUUID() {

		return UUID.randomUUID().toString();
		
	}
	
}
