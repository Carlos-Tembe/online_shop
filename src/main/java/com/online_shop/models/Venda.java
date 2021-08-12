package com.online_shop.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "VENDA")
public class Venda extends AbstractEntity<Long> {

	private int funcionario_id;

	private String codigo;

	private String estado;

	private String data;
//	@ManyToOne
//	@JoinColumn(name = "cliente_id")
	// private Cliente cliente;

	private String local_entrega;

	private String data_entrega;

	private double total;

	private String tipo;

	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
	private List<Item_venda> items = new ArrayList<>();
//
////	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
////	private List<Pagamento> pagamento = new ArrayList<>();

}
