package com.online_shop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "FORNECEDOR")
@Getter
@Setter
public class Fornecedor extends AbstractEntity<Long> {

	@NotNull
	private String nome;

	private String email;

	@NotNull
	private String morada;

	@NotNull
	private String telefone;

	@Column(name = "nuit", nullable = false, length = 9)
	private String nuit;
}
