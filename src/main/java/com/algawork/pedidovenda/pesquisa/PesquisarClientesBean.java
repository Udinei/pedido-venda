package com.algawork.pedidovenda.pesquisa;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algawork.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.filter.ClienteFilter;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;



@Named
@ViewScoped
public class PesquisarClientesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Clientes Clientes;
	private Cliente clienteSelecionado;
	
	private ClienteFilter filtro;
	private List<Cliente> ClientesFiltrados;
	
	
	public PesquisarClientesBean() {
	   filtro = new ClienteFilter();
	}
	
	
	public void pesquisar(){
		ClientesFiltrados = Clientes.filtrados(filtro);		
	}
	
	public void excluir(){
		  Clientes.remover(clienteSelecionado);
		  ClientesFiltrados.remove(clienteSelecionado);
		  
		  FacesUtil.addInfoMessage("Cliente " + clienteSelecionado.getNome() + " excluido com sucesso");
	}




	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}


	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}


	public ClienteFilter getFiltro() {
		return filtro;
	}


	public void setFiltro(ClienteFilter filtro) {
		this.filtro = filtro;
	}


	public List<Cliente> getClientesFiltrados() {
		return ClientesFiltrados;
	}


	public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
		ClientesFiltrados = clientesFiltrados;
	}
	
	
	/*private List<Integer> clientesFiltrados;
	
	public PesquisarClientesBean(){
		clientesFiltrados = new ArrayList<>();
		
		for(int i =0; i < 50; i++){
			this.clientesFiltrados.add(i);
		}
			
		
	}
	
	
	
	
	public List<Integer> getClientesFiltrados(){
		return clientesFiltrados;
	}*/
	
	
}
