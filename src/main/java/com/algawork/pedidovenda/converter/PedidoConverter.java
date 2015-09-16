package com.algawork.pedidovenda.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algawork.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.utils.cdi.CDIServiceLocator;

@FacesConverter(forClass = Pedido.class)
public class PedidoConverter implements Converter {

	// @Inject
	private Pedidos pedidos;

	public PedidoConverter() {
		pedidos = CDIServiceLocator.getBean(Pedidos.class);
	}
	
	
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Pedido retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = pedidos.porId(id);
		}

		return retorno;
	}
	

	
	

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			Pedido pedido = (Pedido) value;
			if (pedido != null) {
				return pedido.getId() == null ? null : pedido.toString();
			}
		
		}
		return "";
	}
//	 @Override
//	    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
//	        if (value != null && !value.isEmpty()) {
//	            return (Pedido) uiComponent.getAttributes().get(value);
//	        }
//	        return null;
//	    }
//
//	    @Override
//	    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
//	        if (value instanceof Pedido) {
//	        	Pedido entity= (Pedido) value;
//	            if (entity != null && entity instanceof Pedido && entity.getId() != null) {
//	                uiComponent.getAttributes().put( entity.getId().toString(), entity);
//	                return entity.getId().toString();
//	            }
//	        }
//	        return "";
//	    }



}
