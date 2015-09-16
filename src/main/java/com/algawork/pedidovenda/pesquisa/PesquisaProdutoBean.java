package com.algawork.pedidovenda.pesquisa;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algawork.pedidovenda.repository.Produtos;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.filter.ProdutoFilter;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;




@Named
@ViewScoped
public class PesquisaProdutoBean implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Inject
	private Produtos produtos;
	
	private Produto produtoSelecionado;
	
	private ProdutoFilter filtro;
	private List<Produto> produtosFiltrados;
	
	
	public PesquisaProdutoBean() {
	   filtro = new ProdutoFilter();
	}
	
	
	public void pesquisar(){
		produtosFiltrados = produtos.filtrados(filtro);		
	}
	
	public void excluir(){
		  produtos.remover(produtoSelecionado);
		  produtosFiltrados.remove(produtoSelecionado);
		  
		  FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getSku() + " excluido com sucesso");
	}
	
	
	public ProdutoFilter getFiltro() {
		return filtro;
	}
	
	public List<Produto> getProdutosFiltrados(){
		return produtosFiltrados;
	}


	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}


	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
	
	
}
