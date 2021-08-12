package com.online_shop.models;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USERS")

public class User extends AbstractEntity<Long> implements UserDetails {

	private static final long SerialVersionUID = 1l;
	@Size(min = 2, message = "O username deve pelo menos ter 2 caracteres")
	private String username;

	@Size(min = 4, message = "A senha deve ter pelo menos 4 caracteres")
	private String password;

	@Transient
	private String confirmPassword;

	@Email(message = "Insira um email valido")
	private String email;

	@Column(name = "telefone")
	@Size(min = 9, message = "O telefone deve ter 9 caracteres")
	private String telefone;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
