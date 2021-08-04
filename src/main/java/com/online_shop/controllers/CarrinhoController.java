package com.online_shop.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.online_shop.models.Artigo_detalhes;
import com.online_shop.models.Item_venda;
import com.online_shop.services.ArtigoDetalhesService;

@Controller
@RequestMapping("carrinho")
@SuppressWarnings("unchecked")
public class CarrinhoController {
	@Autowired
	private ArtigoDetalhesService artigoService;

	@GetMapping("/add/{id}")
	private String add(@PathVariable Long id, HttpSession session, Model model) {
		Artigo_detalhes artigo = artigoService.buscarPorId(id);

		if (session.getAttribute("carrinho") == null) {
			HashMap<Long, Item_venda> carrinho = new HashMap<>();
			carrinho.put(id, new Item_venda(id, artigo.getArtigo().getDescricao(), artigo.getPreco_unitario(), 1));
			session.setAttribute("carrinho", carrinho);
		} else {
			HashMap<Long, Item_venda> carrinho = (HashMap<Long, Item_venda>) session.getAttribute("carrinho");
			if (carrinho.containsKey(id)) {
				double qty = carrinho.get(id).getQuantidade();
				carrinho.put(id,
						new Item_venda(id, artigo.getArtigo().getDescricao(), artigo.getPreco_unitario(), ++qty));
			} else {
				carrinho.put(id, new Item_venda(id, artigo.getArtigo().getDescricao(), artigo.getPreco_unitario(), 1));
				session.setAttribute("carrinho", carrinho);
			}
		}

		HashMap<Long, Item_venda> carrinho = (HashMap<Long, Item_venda>) session.getAttribute("carrinho");
		int tamanho = 0;
		double total = 0;
		for (Item_venda value : carrinho.values()) {
			tamanho += value.getQuantidade();
			total += value.getPreco_unitario();
		}

		model.addAttribute("tamanho", tamanho);
		model.addAttribute("total", total);

		return "fragments/carrinho_view";
	}

	@RequestMapping("/view")
	public String view(HttpSession session, Model model) {
		if (session.getAttribute("carrinho") == null) {
			return "redirect:/";
		}
		HashMap<Long, Item_venda> carrinho = (HashMap<Long, Item_venda>) session.getAttribute("carrinho");
		model.addAttribute("carrinho", carrinho);

		return "carrinho";
	}
}
