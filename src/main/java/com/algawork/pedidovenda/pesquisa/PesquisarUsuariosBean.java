package com.algawork.pedidovenda.pesquisa;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algawork.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.filter.UsuarioFilter;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;



@Named
@ViewScoped
public class PesquisarUsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private Usuarios usuarios;
	
	private Usuario usuarioSelecionado;
	
	private UsuarioFilter filtro;
	private List<Usuario> usuariosFiltrados;
	
	
	public PesquisarUsuariosBean() {
	   filtro = new UsuarioFilter();
	}
	
	
	public void pesquisar(){
		usuariosFiltrados = usuarios.filtrados(filtro);		
	}
	
	public void excluir(){
		  usuarios.remover(usuarioSelecionado);
		  usuariosFiltrados.remove(usuarioSelecionado);
		  
		  FacesUtil.addInfoMessage("Usuario " + usuarioSelecionado.getNome() + " excluido com sucesso");
	}
	
	
	public UsuarioFilter getFiltro() {
		return filtro;
	}
	



	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}


	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	
	/*public PesquisarUsuariosBean(){
		usuariosFiltrados = new ArrayList<>();
		
		for(int i =0; i < 50; i++){
			this.usuariosFiltrados.add(i);
		}
			
		
	}
	*/
	
	
	
	public List<Usuario> getUsuariosFiltrados(){
		return usuariosFiltrados;
	}
	
	
}
