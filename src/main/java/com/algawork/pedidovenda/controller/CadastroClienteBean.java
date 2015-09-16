package com.algawork.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algawork.pedidovenda.repository.Clientes;
import com.algawork.pedidovenda.repository.Enderecos;
import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.model.TipoPessoa;
import com.algaworks.pedidovenda.service.CadastroClienteService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Clientes clientes;

	@Inject
	private CadastroClienteService cadastroClienteService = new CadastroClienteService();

	@Inject
	private Enderecos enderecos;

	private TipoPessoa tipo;
	
	@Produces
	@ClienteEdicao
	
	private Cliente cliente;
	private Endereco endereco;
	private Endereco enderecoSelecionado;
	private String retornaMaskara;
	private String tipoPessoa;
	private boolean edicaoEndereco = false;
	private Endereco novoEndereco;
	private List<Endereco> enderecoRaizes;

	
	public CadastroClienteBean() {
		limpar();
	}

	
	public void salvar() {
		this.cadastroClienteService.salvar(this.cliente);
		limpar();
		inicializar();
		FacesUtil.addInfoMessage("Cliente Salvo com Sucesso");
	}

	public void excluirEndereco() {

		this.cliente.getEnderecos().remove(this.enderecoSelecionado);
		FacesUtil.addInfoMessage("Endereco "
				+ enderecoSelecionado.getLogradouro()
				+ " excluído com sucesso.");

	}

	public void inicializar() {
		if (FacesUtil.isNotPostcack()) {
			// enderecoRaizes = enderecos.raizes();
		}
	}

	public void clienteAlterado(@Observes ClienteAlteradoEvent event){
		this.cliente = event.getCliente();
	}
	
	
	public boolean isEditando() {
		return this.cliente.getId() != null;
	}

	public boolean isAltercaoEndereco() {
		return edicaoEndereco = true;
		

	}
	
	
	 
	public boolean isEnderecoAlterado() {
		this.endereco = new Endereco();
		return edicaoEndereco = false;

	}
	

	private void limpar() {
		this.cliente = new Cliente();
		this.endereco = new Endereco();
		this.novoEndereco = new Endereco();
	}
	

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public List<Endereco> getEnderecoRaizes() {
		return enderecoRaizes;
	}

	public TipoPessoa[] getTiposPessoas() {
		return TipoPessoa.values();
	}

	public void adicionaEndereco() {

		if (!edicaoEndereco) {
			this.cliente.getEnderecos().add(this.endereco);
			endereco.setCliente(cliente);
			this.endereco = new Endereco();
			
		}
	}
	

	public void trocaMaskara() {

		if (cliente.getTipo().equals(TipoPessoa.J)) {
			retornaMaskara = "99.999.999/9999-99";
		} else {
			retornaMaskara = "999.999.999-99";
		}
	}

	public String getRetornaMaskara() {
		return retornaMaskara;
	}

	public void setRetornaMaskara(String retornaMaskara) {
		this.retornaMaskara = retornaMaskara;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Endereco getEnderecoSelecionado() {
		return enderecoSelecionado;
	}

	public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
		this.enderecoSelecionado = enderecoSelecionado;
	}

	public TipoPessoa getTipo() {
		return tipo;
	}

	public void setTipo(TipoPessoa tipo) {
		this.tipo = tipo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public CadastroClienteService getCadastroClienteService() {
		return cadastroClienteService;
	}

	public void setCadastroClienteService(
			CadastroClienteService cadastroClienteService) {
		this.cadastroClienteService = cadastroClienteService;
	}

	public Clientes getClientes() {
		return clientes;
	}

	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}

	public Endereco getNovoEndereco() {
		return this.novoEndereco = new Endereco(); 
	 }
	
//	 public void setNovoEndereco(Endereco novoEndereco) {
//	       this.novoEndereco = novoEndereco;
//	 }

	

}