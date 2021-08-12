package com.online_shop.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.online_shop.models.Item_venda;
import com.online_shop.services.ItemVendaService;

@Controller

public class ItemVendaController {

	@Autowired
	private ItemVendaService itemService;

	@GetMapping
	public String venda(HttpSession session) {

		HashMap<Long, Item_venda> carrinho = (HashMap<Long, Item_venda>) session.getAttribute("carrinho");
		for (Item_venda item : carrinho.values()) {
			itemService.salvar(item);
		}

		return "redirect:/";
	}
}
