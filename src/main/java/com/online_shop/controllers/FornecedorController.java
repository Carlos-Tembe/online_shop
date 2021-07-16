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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.online_shop.models.Fornecedor;
import com.online_shop.services.FornecedorService;

@Controller
@RequestMapping("fornecedores")
public class FornecedorController {

	@Autowired
	private FornecedorService service;

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("fornecedores", service.buscarTodos());
		return "/fornecedor/lista";
	}

	@GetMapping("/pesquisar")
	public String pesquisar(Model model, String search) {

		if (search != null) {
			model.addAttribute("fornecedores", service.buscarPor(search));
		} else {
			model.addAttribute("fornecedores", service.buscarTodos());
		}
		return "/fornecedor/lista";
	}

	@GetMapping("/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("fornecedor", new Fornecedor());
		return "/fornecedor/cadastro";
	}

	@PostMapping("/processar")
	public String processForm(@Validated Fornecedor fornecedor, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			attr.addFlashAttribute("fail", "Ocorreu um erro");
			return "/fornecedor/cadastro";
		}
		service.salvar(fornecedor);
		attr.addFlashAttribute("success", "Fornecedor registado com sucesso");
		return "redirect:/fornecedores";
	}

	@GetMapping("/editar/{id}")
	public String processarEditar(@PathVariable("id") Long id, Model model) {
		model.addAttribute("fornecedor", service.buscarPorId(id));
		return "/fornecedor/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Validated Fornecedor fornecedor, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			attr.addFlashAttribute("fail", "Ocorreu um erro ");
			return "/fornecedor/cadastro";

		}
		service.salvar(fornecedor);
		attr.addFlashAttribute("success", "Fornecedor " + fornecedor.getNome() + " actualizado com sucesso");
		return "redirect:/fornecedores";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, Model model) {
//		if (!service.fornecedorTemArtigos(id)) {
		service.excluir(id);
//		}
		return listar(model);
	}

}
