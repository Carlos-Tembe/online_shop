package com.online_shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.online_shop.models.Categoria;
import com.online_shop.services.CategoriaService;

@Controller
@RequestMapping("categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

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
}
