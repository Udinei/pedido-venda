package com.algawork.pedidovenda.pesquisa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algawork.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.repository.filter.PedidoFilter;



	@Named
	@ViewScoped
	public class PesquisaPedidosBean implements Serializable {

	
		private static final long serialVersionUID = 1L;
		
		@Inject
		private Pedidos pedidos;
		private PedidoFilter filtro;
		
		private List<Pedido> pedidosFiltrados;
		
		
		public PesquisaPedidosBean(){
		      filtro = new PedidoFilter();
		 	  pedidosFiltrados = new ArrayList();
										
		}
		
		public void pesquisar(){
			pedidosFiltrados = pedidos.filtrados(filtro);
		}
		
		
		public List<Pedido> getPedidosFiltrados(){
			return pedidosFiltrados;
		}
		
		public StatusPedido[] getStatuses(){
			return StatusPedido.values();
			
		}
		
		public PedidoFilter getFiltro() {
			return filtro;
		}
	}

