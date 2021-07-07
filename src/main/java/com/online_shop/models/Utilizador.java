package com.online_shop.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@Table(name = "UTILIZADOR")
public class Utilizador extends AbstractEntity<Long> {

	private String nomeUtilizador;
	private String primeiro_nome;
	private String segundo_nome;
	private String senha;
	private String perfil;

}
