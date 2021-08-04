package com.online_shop.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_VENDA")
public class Item_venda extends AbstractEntity<Long> {

	private long artigo_id;
	private String artigo_nome;

	private double preco_unitario;

	private double quantidade;

	private double item_montante;

	public Item_venda(long artigo_id, String artigo_nome, double preco_unitario, double quantidade) {

		super();
		this.artigo_id = artigo_id;
		this.artigo_nome = artigo_nome;
		this.preco_unitario = preco_unitario;
		this.quantidade = quantidade;
	}

}
