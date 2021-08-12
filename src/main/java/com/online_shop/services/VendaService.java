package com.online_shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online_shop.models.Venda;
import com.online_shop.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;

	public Venda salvar(Venda venda) {
		return vendaRepository.save(venda);
	}
}
