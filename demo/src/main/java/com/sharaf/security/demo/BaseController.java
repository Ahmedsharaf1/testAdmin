package com.sharaf.security.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sharaf.security.demo.security.AppUser;


public abstract  class BaseController {

	public AppUser getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (AppUser) authentication.getPrincipal();
		
	}
	
	public AppUser getCurrentUserAuth() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (AppUser) authentication.getAuthorities();
		
	}
	
}
