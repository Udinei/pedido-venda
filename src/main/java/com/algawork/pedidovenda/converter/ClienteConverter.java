package com.algawork.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algawork.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.utils.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {

	// @Inject
	private Clientes clientes;

	public ClienteConverter() {
		clientes = CDIServiceLocator.getBean(Clientes.class);
	}

//    public Object getAsObject(FacesContext ctx, UIComponent component,
//            String value) {
//        if (value != null) {
//            return this.getAttributesFrom(component).get(value);
//        }
//        return null;
//    }
// 
//    public String getAsString(FacesContext ctx, UIComponent component,
//            Object value) {
// 
//        if (value != null && ! "".equals(value)) {
//            AbstractEntity entity = (AbstractEntity) value;
// 
//            if (entity.getId() != null) {
//                this.addAttribute(component, entity);
// 
//                if (entity.getId() != null) {
//                    return String.valueOf(entity.getId());
//                }
//                return (String) value;
//            }
//        }
//        return "";
//    }
// 
//    private void addAttribute(UIComponent component, AbstractEntity o) {
//        this.getAttributesFrom(component).put(o.getId().toString(), o);
//    }
// 
//    private Map<String, Object> getAttributesFrom(UIComponent component) {
//        return component.getAttributes();
//    }	

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
	
		Cliente retorno = null;

                
		if (value != null) {
			Long id = new Long(value);
			retorno = clientes.porId(id);
		}

		return retorno;
	}

	        


	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			Cliente cliente = (Cliente) value;
			if (cliente != null) {
				return cliente.getId() == null ? null : cliente.toString();
			}
		
		}
		return "";
	}

}
