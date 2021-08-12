package com.online_shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online_shop.models.User;
import com.online_shop.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void salvar(User user) {
		userRepository.save(user);
	}

	public List<User> buscarTodos() {
		return userRepository.findAll();
	}

	public void excluir(Long id) {
		userRepository.deleteById(id);

	}

	public User buscarPorId(Long id) {
		return userRepository.getById(id);
	}
}
