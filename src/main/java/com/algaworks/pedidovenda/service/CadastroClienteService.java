package com.algaworks.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algawork.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.util.jpa.Transactional;


public class CadastroClienteService implements Serializable {
	
	
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Clientes clientes;
	
	
	@Transactional
	public Cliente salvar(Cliente cliente){
		Cliente clienteExistente = clientes.porEmail(cliente.getEmail());
		
		if(clienteExistente != null && !clienteExistente.equals(cliente)){
			throw new NegocioException("Já existe um Cliente com o email informado");
		}
		
		return clientes.guardar(cliente);
	}

}
