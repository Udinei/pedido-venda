package com.algawork.pedidovenda.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;



@ManagedBean
@RequestScoped
public class PesquisarGruposBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Integer> gruposFiltrados;
	
	public PesquisarGruposBean(){
		gruposFiltrados = new ArrayList();
		
		for(int i =0; i < 50; i++){
			this.gruposFiltrados.add(i);
		}
			
		
	}
	
	
	
	
	public List<Integer> getGruposFiltrados(){
		return gruposFiltrados;
	}
	
	
}
