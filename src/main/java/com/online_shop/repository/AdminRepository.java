package com.online_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_shop.models.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	Admin findByUsername(String username);
}
