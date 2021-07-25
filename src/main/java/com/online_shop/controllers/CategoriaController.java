package com.online_shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.online_shop.models.Artigo;
import com.online_shop.models.Categoria;
import com.online_shop.services.ArtigoService;
import com.online_shop.services.CategoriaService;

@Controller
@RequestMapping("categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	@Autowired
	private ArtigoService artigoService;

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("categorias", service.buscarTodos());
		return "/categoria/lista";
	}

	@GetMapping("/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("categoria", new Categoria());
		return "/categoria/cadastro";
	}

	@PostMapping("/processar")
	public String processForm(@Validated Categoria categoria, BindingResult result) {
		if (result.hasErrors()) {
			return "/categoria/cadastro";
		}
		service.salvar(categoria);
		return "redirect:/categorias/";
	}

	@GetMapping("/editar/{id}")
	public String processarEditar(@PathVariable("id") Long id, Model model) {
		model.addAttribute("categoria", service.buscarPorId(id));
		return "/categoria/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Validated Categoria categoria, BindingResult result) {
		if (result.hasErrors()) {
			return "/categoria/cadastro";
		}
		service.salvar(categoria);
		return "redirect:/categorias/";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, Model model) {
		if (!service.categoriaTemArtigos(id)) {
			service.excluir(id);
		}
		return listar(model);
	}

	@GetMapping("/{id}")
	public String categoria(@PathVariable Long id, Model model,
			@RequestParam(value = "page", required = false) Integer p) {
		int perPage = 3;
		int page = (p != null) ? p : 0;
		Pageable pageable = PageRequest.of(page, perPage);
		long count = 0;
		if (id == 0) {
			Page<Artigo> artigos = artigoService.buscarTodos(pageable);
			count = artigoService.total();
			model.addAttribute("artigos", artigos);
			model.addAttribute("count", count);
		} else {
			Categoria categoria = service.buscarPorId(id);
			if (categoria == null) {
				return "redirect:/index";
			}
			String nomeCategoria = categoria.getNome();
			List<Artigo> artigos = categoria.getArtigos();
			count = artigos.size();
			model.addAttribute("artigos", artigos);
			model.addAttribute("nomeCategoria", nomeCategoria);
		}

		double pageCount = Math.ceil((double) count / (double) perPage);
		model.addAttribute("categorias", service.buscarTodos());
		model.addAttribute("pageCount", (int) pageCount);
		model.addAttribute("perPage", perPage);
		model.addAttribute("page", page);

		return "products";
	}

}
