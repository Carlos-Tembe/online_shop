package com.online_shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online_shop.models.Artigo_movimento;
import com.online_shop.repository.Artigo_movimentoRepository;

@Service
public class Stock_movimentoService {

	@Autowired
	private Artigo_movimentoRepository repository;

	@Autowired
	private UtilizadorService userService;

	public void salvar(Artigo_movimento movimento) {

		movimento.setUser(userService.buscarPorId((long) 1));
		repository.save(movimento);
	}

	public void excluir(Long id) {
		repository.deleteById(id);
	}

	public Artigo_movimento buscarPorId(Long id) {
		return repository.getById(id);
	}

	public List<Artigo_movimento> buscarTodos() {
		return repository.findAll();
	}
}
