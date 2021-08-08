package com.online_shop.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online_shop.models.Fornecedor;
import com.online_shop.repository.FornecedorRepository;

@Service
@Transactional
public class FornecedorService {

	@Autowired
	private FornecedorRepository repository;

	public void salvar(Fornecedor fornecedor) {
		repository.save(fornecedor);
	}

	public void excluir(Long id) {
		repository.deleteById(id);

	}

	public Fornecedor buscarPorId(Long id) {
		return repository.getById(id);
	}

	public List<Fornecedor> buscarTodos() {
		return repository.findAll();
	}

	public List<Fornecedor> buscarPor(String search) {
		return repository.findBySearch(search);
	}

}
