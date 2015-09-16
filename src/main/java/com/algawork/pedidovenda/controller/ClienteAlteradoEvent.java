package com.algawork.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Cliente;

public class ClienteAlteradoEvent {

	private Cliente cliente;

	public ClienteAlteradoEvent(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

}
