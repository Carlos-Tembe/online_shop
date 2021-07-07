package com.online_shop.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online_shop.models.Categoria;
import com.online_shop.repository.CategoriaRepository;

@Service
@Transactional
public class CategoriaService {
	@Autowired
	private CategoriaRepository repository;

	public void salvar(Categoria categoria) {
		repository.save(categoria);

	}

	public void excluir(Long id) {
		repository.deleteById(id);
	}

	public Categoria buscarPorId(Long id) {
		return repository.getById(id);
	}

	public List<Categoria> buscarTodos() {
		return repository.findAll();
	}

	public boolean categoriaTemArtigos(Long id) {
		if (buscarPorId(id).getArtigos().isEmpty()) {
			return false;
		}
		return true;
	}

}
