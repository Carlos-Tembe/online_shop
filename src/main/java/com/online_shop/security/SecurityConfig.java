package com.online_shop.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll();

//		http.authorizeRequests().antMatchers("/categorias/**").access("hasRole('ROLE_USER')").antMatchers("/")
//				.access("permitAll");

	}
}
