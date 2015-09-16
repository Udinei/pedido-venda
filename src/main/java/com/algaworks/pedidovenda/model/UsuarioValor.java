package com.algaworks.pedidovenda.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class UsuarioValor implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeUsuario;
	private BigDecimal valorPedidosUsuario;

		

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public BigDecimal getValorPedidosUsuario() {
		return valorPedidosUsuario;
	}

	public void setValorPedidosUsuario(BigDecimal valorPedidosUsuario) {
		this.valorPedidosUsuario = valorPedidosUsuario;
	}

	
		
}
