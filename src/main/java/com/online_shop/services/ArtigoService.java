package com.online_shop.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.online_shop.models.Artigo;
import com.online_shop.repository.ArtigoRepository;

@Service
@Transactional
public class ArtigoService {
	@Autowired
	private ArtigoRepository repository;

	private String[] cols = { "id", "descricao", "codigo_barra", "preco_unitario", "quan_min", "quant_armazem",
			"quant_loja", "foto" };

	public Map<String, Object> execute(HttpServletRequest request) {

		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		int draw = Integer.parseInt(request.getParameter("draw"));
		int current = currentPage(start, length);

		String column = columnName(request);
		Sort.Direction direction = orderBy(request);

		Pageable pageable = PageRequest.of(current, length, direction, column);
		Page<Artigo> page = queryBy(pageable);
		Map<String, Object> json = new LinkedHashMap<>();

		json.put("draw", draw);
		json.put("recordsTotal", page.getTotalElements());
		json.put("recordsFiltered", page.getTotalElements());
		json.put("data", page.getContent());
		return json;
	}

	private Page<Artigo> queryBy(Pageable pageable) {
		return repository.findAll(pageable);
	}

	private Direction orderBy(HttpServletRequest request) {
		String order = request.getParameter("order[0][dir]");
		Sort.Direction sort = Sort.Direction.ASC;
		if (order.equalsIgnoreCase("desc")) {
			sort = Sort.Direction.DESC;
		}
		return sort;
	}

	private String columnName(HttpServletRequest request) {
		int iCol = Integer.parseInt(request.getParameter("order[0][column]"));
		return cols[iCol];
	}

	private int currentPage(int start, int length) {
		// 0 1 2
		// 0-9 10-19 20-29
		return start / length;
	}

	public Artigo salvar(Artigo artigo) {
		return repository.save(artigo);

	}

	public void excluir(Long id) {
		repository.deleteById(id);

	}

	public Artigo buscarPorId(Long id) {
		return repository.getById(id);
	}

	public List<Artigo> buscarTodos() {
		return repository.findAll();
	}

	public List<Artigo> findBySearch(String search) {
		return repository.findBySearch(search);
	}

	public boolean artigoTemDetalhes(Long id) {
		if (buscarPorId(id).getDetalhes().isEmpty()) {
			return false;
		}
		return true;
	}

}
