package com.gabrielmint.cursomc.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.gabrielmint.cursomc.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
		return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			return null;
		}
	}
	
}
