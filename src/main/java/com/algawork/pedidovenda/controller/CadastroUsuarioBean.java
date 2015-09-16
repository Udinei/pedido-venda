package com.algawork.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algawork.pedidovenda.repository.Grupos;
import com.algawork.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.service.CadastroUsuarioService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
    
	@Inject
	private Usuarios usuarios;
	@Inject
	private CadastroUsuarioService cadastroUsuarioService = new CadastroUsuarioService();
	
	
	private List<Grupo> gruposDisponiveis;
	
	@Inject
	private Grupos grupos;
	private Grupo novoGrupo;
	
	private Usuario usuario;

	private Grupo grupoSelecionado;
	
	
	public CadastroUsuarioBean() {
	       limpar();
	    
	}
	
	public void salvar(){
		this.usuario = cadastroUsuarioService.salvar(this.usuario);
		limpar();
		FacesUtil.addInfoMessage("Usuário salvo com sucesso");
		
	}
	
	
	public void limpar(){
		usuario = new Usuario();
		novoGrupo = null;
				
	}

	
    public boolean isEditando(){
    	return this.usuario.getId() != null;
    	//return true;
    	
    
    }
	
	
	public void inicializar(){
		if (FacesUtil.isNotPostcack())
		{
			System.out.println("inicializando o pre-render...");
			gruposDisponiveis = grupos.todos();
		}
	  	  
		  
	}
	
	public void adicionarGrupo(){
		
		if(novoGrupo != null){
			if(this.usuario.getGrupos().contains(novoGrupo)){
				FacesUtil.addErrorMessage("Grupo " + novoGrupo.getDescricao() + " já incluido para esse usuário");
			}else{
				  this.usuario.getGrupos().add(novoGrupo);
			}
		}
	}

	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public CadastroUsuarioService getCadastroUsuarioService() {
		return cadastroUsuarioService;
	}

	public void setCadastroUsuarioService(
			CadastroUsuarioService cadastroUsuarioService) {
		this.cadastroUsuarioService = cadastroUsuarioService;
	}

	public List<Grupo> getGruposDisponiveis() 
	{
		return gruposDisponiveis;
	}

	public void setGruposDisponiveis(List<Grupo> gruposDisponiveis) 
	{
		this.gruposDisponiveis = gruposDisponiveis;
	}
	

	public Grupos getGrupos() {
		return grupos;
	}

	public void setGrupos(Grupos grupos) {
		this.grupos = grupos;
		
		
	}

	public Grupo getNovoGrupo() {
		return novoGrupo;
	}

	public void setNovoGrupo(Grupo novoGrupo) {
		this.novoGrupo = novoGrupo;
	}
	
	public void removerGrupoLista(){
		  usuario.getGrupos().remove(grupoSelecionado);
    	  FacesUtil.addInfoMessage("Grupo " + grupoSelecionado.getNome() + " removido com sucesso");
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}	
}
