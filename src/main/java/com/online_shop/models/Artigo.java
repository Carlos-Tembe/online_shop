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
@Table(name = "ARTIGO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Artigo extends AbstractEntity<Long> {

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@Column(nullable = true, length = 100)
	private String descricao;

	@Column(nullable = true, length = 45)
	private String codigo_barra;

	@Column(nullable = true, length = 64)
	private String foto;

}
