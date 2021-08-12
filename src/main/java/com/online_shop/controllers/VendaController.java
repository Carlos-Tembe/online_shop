package com.online_shop.controllers;

import java.time.LocalDate;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.online_shop.models.Item_venda;
import com.online_shop.models.Venda;
import com.online_shop.services.ItemVendaService;
import com.online_shop.services.VendaService;

@Controller
@RequestMapping("/venda")
public class VendaController {

	@Autowired
	private VendaService vendaService;

	@Autowired
	private ItemVendaService itemVendaService;

	@PostMapping
	public String registarVenda(HttpSession session, Venda venda) {
		venda.setTipo("Encomenda");
		venda.setEstado("Vendido");
		venda.setData(LocalDate.now().toString());
		Venda v = vendaService.salvar(venda);

		HashMap<Long, Item_venda> carrinho = (HashMap<Long, Item_venda>) session.getAttribute("carrinho");
		for (Item_venda item : carrinho.values()) {
			item.setVenda(v);
			itemVendaService.salvar(item);
		}

		return "redirect:/carrinho/clear";
	}
}
