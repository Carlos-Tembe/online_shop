package com.online_shop.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.DocumentException;
import com.online_shop.models.Artigo;
import com.online_shop.models.ArtigoPDFExporter;
import com.online_shop.models.Utilizador;
import com.online_shop.services.ArtigoService;
import com.online_shop.services.UserService;
import com.online_shop.services.UtilizadorService;

@Controller
@RequestMapping("utilizadores")
public class UtilizadorController {

	@Autowired
	private UtilizadorService service;
	@Autowired
	private UserService userService;

	@Autowired
	private ArtigoService artigoService;

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("utilizadores", userService.buscarTodos());
		return "/utilizador/lista";
	}
//	@GetMapping
//	public String listar(Model model) {
//		model.addAttribute("utilizadores", service.buscarTodos());
//		return "/utilizador/lista";
//	}

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
		userService.excluir(id);
//		}
		return listar(model);
	}

	@GetMapping("/export")
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd_HH:mm:ss");
		String file_currentDate = dateFormat.format(new java.util.Date());

		response.setContentType("application/pdf");
		String headerkey = "";
		String headerValue = "attachment; filename:artigos_" + file_currentDate + ".pdf";
		response.setHeader(headerkey, headerValue);

		List<Artigo> artigos = artigoService.buscarTodos();

		ArtigoPDFExporter exporter = new ArtigoPDFExporter(artigos);
		exporter.export(response);

	}

}
