package com.online_shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public void editar(Artigo_detalhes artigo) {
		Artigo_detalhes artig = buscarPorId(artigo.getId());
		artig.setTamanho(artigo.getTamanho());
		artig.setPreco_unitario(artigo.getPreco_unitario());
		artig.setCor(artigo.getCor());
		repository.save(artig);

	}

	public void excluir(Long id) {
		repository.deleteById(id);

	}

	public Artigo_detalhes buscarPorId(Long id) {
		return repository.getById(id);
	}

	public boolean removerDaLoja(Long artigo_id, double qty) {
		return subtrairNaLoja(artigo_id, qty) && adicionarStock(artigo_id, qty);
	}

	public boolean removerDoStock(Long artigo_id, double qty) {
		return subtrairNoStock(artigo_id, qty) && adicionarNaLoja(artigo_id, qty);
	}

	public boolean adicionarNaLoja(Long artigo_id, double qty) {
		boolean resultado = false;
		Artigo_detalhes artigo = buscarPorId(artigo_id);
		if (qty > 0) {
			double quant = qty + artigo.getQuant_loja();
			artigo.setQuant_loja(quant);
			salvar(artigo);
			resultado = true;
		}
		return resultado;

	}

	public boolean subtrairNaLoja(Long artigo_id, double qty) {
		boolean resultado = false;
		Artigo_detalhes artigo = buscarPorId(artigo_id);
		if (qty > 0 && artigo.getQuant_loja() > qty) {
			double quant = artigo.getQuant_loja() - qty;
			artigo.setQuant_loja(quant);
			salvar(artigo);
			resultado = true;
		}
		return resultado;

	}

	public boolean adicionarStock(Long artigo_id, double qty) {
		boolean resultado = false;
		Artigo_detalhes artigo = buscarPorId(artigo_id);
		if (qty > 0) {
			double quant = qty + artigo.getQuant_stock();
			artigo.setQuant_stock(quant);
			salvar(artigo);
			resultado = true;
		}
		return resultado;

	}

	public Artigo_detalhes adicionarStock(Artigo_detalhes artigo_detalhe, double qty) {
		artigo_detalhe.setQuant_stock(qty);
		return artigo_detalhe;
	}

	public boolean subtrairNoStock(Long artigo_id, double qty) {
		boolean resultado = false;
		Artigo_detalhes artigo = buscarPorId(artigo_id);
		if (qty > 0 && artigo.getQuant_stock() > qty) {
			double quant = artigo.getQuant_stock() - qty;
			artigo.setQuant_stock(quant);
			salvar(artigo);
			resultado = true;
		}
		return resultado;

	}

	public boolean mudarStatus(Artigo_detalhes detalhe) {

		if (detalhe.getPreco_unitario() > 0.0) {
			if (detalhe.getEstado() == true) {
				detalhe.setEstado(false);
			} else {
				detalhe.setEstado(true);
			}
			salvar(detalhe);
			return true;
		}
		return false;

	}

	public List<Artigo_detalhes> buscarTodos() {
		return repository.findAll();
	}

	public List<Artigo_detalhes> buscarTodosPorArtigoId(Long id) {
		return repository.findByArtigoId(id);
	}

}
