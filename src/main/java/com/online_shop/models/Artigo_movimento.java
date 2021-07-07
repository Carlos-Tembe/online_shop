package com.online_shop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "ARTIGO_MOVIMENTO")
@Getter
@Setter
public class Artigo_movimento extends AbstractEntity<Long> {

	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Utilizador user;

	@Column(nullable = true, length = 8)
	private String tipo;

	private double quantidade;

	private double quant_inicial;

	private double quant_final;

	private double preco_compra;

	private double preco_venda;

	public Artigo_movimento() {
		this.tipo = "entrada";
	}

}
