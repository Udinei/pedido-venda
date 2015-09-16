package com.algaworks.pedidovenda.model;

public enum FormaPagamento {
 DINHEIRO("Dinheiro"),
 CARTAO_CREDITO("Cartão de Crédito"),
 CARTAO_DEBITO("Cartão de Débito"),
 CHEQUE("Cheque"),
 BOLETO("Boleto"),
 DEPOSITO_BANCARIO("Deposito Banqueiro");
 
 private String descricao;
 
 private FormaPagamento(String descricao) {
	this.descricao = descricao; 
}

public String getDescricao() {
	return descricao;
}
 
 
 
}
