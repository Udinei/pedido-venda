package com.algawork.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algawork.pedidovenda.repository.Produtos;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.utils.cdi.CDIServiceLocator;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

	// @Inject
	private Produtos produtos;

	public ProdutoConverter() {
		produtos = CDIServiceLocator.getBean(Produtos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Produto retorno = null;

		try {
				if (value != null) {
					Long id = new Long(value);
					retorno = produtos.porId(id);
				}
		} catch (NumberFormatException e) {
			  FacesUtil.addErrorMessage("Produto "+ value +" excluido do pedido. Produto inválido.");
		}	
		 

		
	

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			Produto produto = (Produto) value;
			if (produto != null) {
				return produto.getId() == null ? null : produto.toString();
			}
		
		}
		return "";
	}

}
