package com.online_shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.online_shop.models.Artigo_detalhes;
import com.online_shop.models.Artigo_movimento;
import com.online_shop.models.Fornecedor;
import com.online_shop.services.ArtigoDetalhesService;
import com.online_shop.services.FornecedorService;
import com.online_shop.services.Stock_movimentoService;
import com.online_shop.services.UtilizadorService;

@Controller
@RequestMapping("artigo/movimentos")
public class Artigo_movimentoController {

	@Autowired
	private Stock_movimentoService movimentoService;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private UtilizadorService userService;

	@Autowired
	private ArtigoDetalhesService artigoDetalheService;

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("movimentos", movimentoService.buscarTodos());
		return "/artigo/lista";
	}

	@GetMapping("/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("movimento", new Artigo_movimento());
		return "/artigo/entrada";
	}

	@PostMapping("/processar")
	public String salvar(@Validated Artigo_movimento movimento, BindingResult result) {

		Artigo_detalhes detalhe = artigoDetalheService.buscarPorId(movimento.getArtigo().getId());
		if (result.hasErrors()) {
			return "/artigo/entrada";
		}

		artigoDetalheService.adicionarStock(detalhe.getId(), movimento.getQuantidade());
		movimento.setQuant_inicial(detalhe.getQuant_stock());
		movimento.setPreco_venda(detalhe.getPreco_unitario());
		double quant_final = detalhe.getQuant_stock() + movimento.getQuantidade();
		movimento.setQuant_final(quant_final);
		movimento.setUser(userService.buscarPorId((long) 1));
		movimentoService.salvar(movimento);
		return "redirect:/artigos/detalhes/" + detalhe.getArtigo().getId();
	}

	@GetMapping("/editar/{id}")
	public String processarEditar(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movimento", movimentoService.buscarPorId(id));
		return "/artigo/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Validated Artigo_movimento movimento, BindingResult result) {
		if (result.hasErrors()) {
			return "/artigo/cadastro";
		}
		movimentoService.salvar(movimento);
		return "redirect:/artigo/movimentos";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, Model model) {
		movimentoService.excluir(id);
		return listar(model);
	}

	@ModelAttribute("fornecedores")
	public List<Fornecedor> listaFornecedor() {
		return fornecedorService.buscarTodos();
	}

//	@ModelAttribute("users")
//	public List<Fornecedor> listaFornecedor() {
//		System.out.println(fornecedorService.buscarTodos().size());
//		return fornecedorService.buscarTodos();
//	}
}
