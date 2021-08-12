package com.online_shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online_shop.models.Item_venda;
import com.online_shop.repository.ItemVendaRepository;

@Service
public class ItemVendaService {

	@Autowired
	private ItemVendaRepository itemRepo;

	public void salvar(Item_venda item) {
		itemRepo.save(item);
	}
}
