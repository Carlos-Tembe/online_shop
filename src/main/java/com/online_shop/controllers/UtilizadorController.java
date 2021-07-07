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

import com.online_shop.models.Utilizador;
import com.online_shop.services.UtilizadorService;

@Controller
@RequestMapping("utilizadores")
public class UtilizadorController {

	@Autowired
	private UtilizadorService service;

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("utilizadores", service.buscarTodos());
		return "/utilizador/lista";
	}

	@GetMapping("/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("utilizador", new Utilizador());
		return "/utilizador/cadastro";
	}

	@PostMapping("/processar")
	public String processForm(@Validated Utilizador utilizador, BindingResult result) {
		if (result.hasErrors()) {
			return "/utilizador/cadastro";
		}
		service.salvar(utilizador);
		return "redirect:/utilizadores";
	}

	@GetMapping("/editar/{id}")
	public String processarEditar(@PathVariable("id") Long id, Model model) {
		model.addAttribute("utilizador", service.buscarPorId(id));
		return "/utilizador/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Validated Utilizador utilizador, BindingResult result) {
		if (result.hasErrors()) {
			return "/utilizador/cadastro";
		}
		service.salvar(utilizador);
		return "redirect:/utilizadores";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, Model model) {
//		if (!service.utilizadorTemArtigos(id)) {
		service.excluir(id);
//		}
		return listar(model);
	}

}
