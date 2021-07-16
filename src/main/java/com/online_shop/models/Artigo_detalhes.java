package com.online_shop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "ARTIGO_DETALHES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Artigo_detalhes extends AbstractEntity<Long> {

	@ManyToOne
	@JoinColumn(name = "artigo_id")
	private Artigo artigo;

	@Column(nullable = true, length = 100)
	private String cor;

	private double preco_unitario;

	private double quant_loja;

	private double quant_stock;

	private boolean estado;

	private String tamanho;

	public Artigo_detalhes(Artigo artigo) {
		this.artigo = artigo;
	}

	public boolean getEstado() {
		return estado;
	}

}
