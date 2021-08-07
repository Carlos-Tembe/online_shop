package com.online_shop.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_VENDA")
public class Item_venda extends AbstractEntity<Long> {

	private long artigo_id;
	private String artigo_nome;

	private double preco_unitario;

	private double quantidade;

	private double item_montante;
	private String foto;
	private Artigo_detalhes detalheArtigo;

	public Item_venda(long artigo_id, String artigo_nome, double preco_unitario, double quantidade, String foto,
			double item_montante) {

		super();
		this.artigo_id = artigo_id;
		this.artigo_nome = artigo_nome;
		this.preco_unitario = preco_unitario;
		this.quantidade = quantidade;
		this.item_montante = item_montante;
		this.foto = foto;
	}

	public Item_venda(long artigo_id, String artigo_nome, double preco_unitario, double quantidade, String foto) {

		super();
		this.artigo_id = artigo_id;
		this.artigo_nome = artigo_nome;
		this.preco_unitario = preco_unitario;
		this.quantidade = quantidade;
		this.foto = foto;
	}

	public Item_venda(Artigo_detalhes detalhes, double quantidade) {

		super();
		this.detalheArtigo = detalhes;
		this.quantidade = quantidade;
	}

}
