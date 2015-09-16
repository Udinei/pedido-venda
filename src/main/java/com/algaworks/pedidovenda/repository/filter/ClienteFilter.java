package com.algaworks.pedidovenda.repository.filter;

import java.io.Serializable;

public class ClienteFilter implements Serializable {
	
	
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private String doc_receita_federal;
	private String tipo;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDoc_receita_federal() {
		return doc_receita_federal;
	}

	public void setDoc_receita_federal(String doc_receita_federal) {
		this.doc_receita_federal = doc_receita_federal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

     


}
