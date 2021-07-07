package com.online_shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.online_shop.models.Artigo_detalhes;
import com.online_shop.repository.ArtigoDetalhesRepository;

@Service
public class ArtigoDetalhesService {

	@Autowired
	private ArtigoDetalhesRepository repository;

	public void salvar(Artigo_detalhes artigo) {
		repository.save(artigo);
	}

	public void excluir(Long id) {
		repository.deleteById(id);

	}

	public Artigo_detalhes buscarPorId(Long id) {
		return repository.getById(id);
	}

	public List<Artigo_detalhes> buscarTodos() {
		return repository.findAll();
	}

	public List<Artigo_detalhes> buscarTodosPorArtigoId(Long id) {
		return repository.findByArtigoId(id);
	}
}
