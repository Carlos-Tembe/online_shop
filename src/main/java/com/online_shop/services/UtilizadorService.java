package com.online_shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online_shop.models.Utilizador;
import com.online_shop.repository.UtilizadorRepository;

@Service
public class UtilizadorService {

	@Autowired
	private UtilizadorRepository repository;

	public void salvar(Utilizador utilizador) {
		repository.save(utilizador);
	}

	public void excluir(Long id) {
		repository.deleteById(id);

	}

	public Utilizador buscarPorId(Long id) {
		return repository.getById(id);
	}

	public List<Utilizador> buscarTodos() {
		return repository.findAll();
	}
}
