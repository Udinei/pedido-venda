package com.algawork.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.algawork.pedidovenda.repository.Categorias;
import com.algaworks.pedidovenda.model.Categoria;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.service.CadastroProdutoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;


@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
 
	@Inject
	private Categorias categoria;
	@Inject
	private CadastroProdutoService cadastroProdutoService;
	
	private Produto produto;
	private List<Categoria> categoriasRaizes;
	private List<Categoria> subcategorias;
	
	private Categoria categoriaPai;
	
	public CadastroProdutoBean(){
		limpar();
		
	}
	
	public void inicializar(){
	  	  
	  if(FacesUtil.isNotPostcack()){
          categoriasRaizes = categoria.raizes();
          
          if(this.categoriaPai != null){
        	  carregarSubcategorias();  
          }
	  }
		  
	}
	
	
	public Produto getProduto() {
		return produto;
	}

	
	public void setProduto(Produto produto) {
		this.produto = produto;
		
		if(this.produto !=null){
			this.categoriaPai = this.produto.getCategoria().getCategoriaPai();	
		}
		 
	}
    public boolean isEditando(){
    	return this.produto.getId() != null;
    	
    
    }

	public void salvar(){
		this.produto = cadastroProdutoService.salvar(this.produto);
		limpar();
		FacesUtil.addInfoMessage("Produto salvo com sucesso");
		
	}
	
	
	public void limpar(){
		produto = new Produto();
		categoriaPai = null;
		subcategorias = new ArrayList();		
	}


	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}

	@NotNull
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}
	
	public void carregarSubcategorias(){
		subcategorias = categoria.subcategoriasDe(categoriaPai);
				
				
	}
	
	

}
