package com.online_shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.online_shop.models.Admin;
import com.online_shop.models.User;
import com.online_shop.repository.AdminRepository;
import com.online_shop.repository.UserRepository;

@Service
public class userRepositoryUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AdminRepository adminRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		Admin admin = adminRepo.findByUsername(username);

		if (user != null) {
			return user;
		}
		if (admin != null) {
			return admin;
		}
		throw new UsernameNotFoundException("Utilizador " + username + " nao existe!");
	}

}
