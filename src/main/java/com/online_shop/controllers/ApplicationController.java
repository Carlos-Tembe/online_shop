package com.online_shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.online_shop.models.Utilizador;
import com.online_shop.services.ArtigoService;

@Controller
@RequestMapping("onlineshop")
public class ApplicationController {

	@Autowired
	private ArtigoService service;

	@GetMapping
	public String index(Model model) {
		model.addAttribute("artigos", service.buscarTodos());
		return "index";
	}

	@GetMapping("/admin")
	public String goHome() {
		return "admin/admin";
	}
	@PostMapping
	public String entrar() {
		return "admin/admin";
	
	}

	@GetMapping("/login")
	public String loginView() {
		return "utilizador/login";
	}

	@GetMapping("cadastrar")
	public String utilizadorCadastro(Model model) {
		model.addAttribute("utilizador", new Utilizador());
		return "utilizador/cadastro";
	}

}
