package com.algawork.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.algawork.pedidovenda.repository.Clientes;
import com.algawork.pedidovenda.repository.Produtos;
import com.algawork.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.EnderecoEntrega;
import com.algaworks.pedidovenda.model.FormaPagamento;
import com.algaworks.pedidovenda.model.ItemPedido;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.service.CadastroPedidoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.validation.SKU;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;

	@Inject
	private Clientes clientes;

	@Inject
	private Produtos produtos;

			
	@Inject
	private CadastroPedidoService cadastroPedidoService;

	private Produto produtoLinhaEditavel;

	private String sku;
	
	@Produces
	@PedidoEdicao
	private Pedido pedido;

	private List<Usuario> vendedores;

	public CadastroPedidoBean() {
		limpar();

	}

	public void recalcularPedido() {
		this.pedido.recalcularValorTotal();
	}

	public void inicializar() {
		if (FacesUtil.isNotPostcack()) {
			this.vendedores = this.usuarios.vendedores();
			this.pedido.adicionarItemVazio();
			this.recalcularPedido();
		}
	}

	public List<Cliente> completarCliente(String nome) {
		return this.clientes.porNome(nome);

	}

	private void limpar() {
		pedido = new Pedido();
		pedido.setEnderecoEntrega(new EnderecoEntrega());
	}

	
	public void pedidoAlterado(@Observes PedidoAlteradoEvent event){
		this.pedido = event.getPedido();
	}
	
	public void carregarProdutoLinhaEditavel() {
		ItemPedido item = this.pedido.getItens().get(0);

		if (this.produtoLinhaEditavel != null) {
			if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtil
						.addErrorMessage("Já existe um item no pedido com o o produto informado");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel
						.getValorUnitario());

				this.pedido.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;

				this.pedido.recalcularValorTotal();
			}
		}

	}

	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemPedido item : this.getPedido().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}

		return existeItem;
	}

	public List<Produto> completarProduto(String nome) {
		return this.produtos.porNome(nome);
	}

	public void atualizarQuantidade(ItemPedido item, int linha) {
		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				this.getPedido().getItens().remove(linha);
			}
		}

		this.pedido.recalcularValorTotal();

	}

	public FormaPagamento[] getFormaPagamento() {
		return FormaPagamento.values();
	}

	public List<Usuario> getVendedores() {
		return vendedores;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public void salvar() {
		this.pedido.removerItemVazio();

		try {
			this.pedido = this.cadastroPedidoService.salvar(this.pedido);
			FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
			// throw new
			// NegocioException("O pedido não pode ser salvo, ainda não foi implementado");
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}

	public boolean isEditando() {
		return this.pedido.getId() != null;

	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	public void carregarProdutoPorSku() {
		if (StringUtils.isNotEmpty(this.sku)) {
			this.produtoLinhaEditavel = this.produtos.porSKU(this.sku);
			this.carregarProdutoLinhaEditavel();

		}
	}

	@SKU
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

}
