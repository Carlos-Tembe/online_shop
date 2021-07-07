package com.online_shop.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "CATEGORIA")
@Getter
@Setter
public class Categoria extends AbstractEntity<Long> {

	@NotNull
	private String nome;

	@Column(nullable = false, length = 150)
	private String descricao;

	@JsonIgnore
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	private List<Artigo> artigos;

}
