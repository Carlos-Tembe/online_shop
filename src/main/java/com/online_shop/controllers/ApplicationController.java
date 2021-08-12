package com.online_shop.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.online_shop.models.Utilizador;
import com.online_shop.services.ArtigoService;
import com.online_shop.services.CategoriaService;

@Controller
@RequestMapping("/")
public class ApplicationController {

	@Autowired
	private ArtigoService service;
	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public String index(Model model) {
		return "redirect:/artigos/0";
	}

	@GetMapping("onlineshop")
	public String menu(Model model, Principal principal) {
		if (principal != null) {
			model.addAttribute("principal", principal.getName());
		}
		return "redirect:/artigos/0";
	}

	@GetMapping("onlineshop/admin")
	public String goHome() {
		return "admin/admin";
	}

	@GetMapping("/login")
	public String login() {
		return "utilizador/login";
	}

	@GetMapping("cadastrar")
	public String utilizadorCadastro(Model model) {
		model.addAttribute("utilizador", new Utilizador());
		return "utilizador/cadastro";
	}

}
