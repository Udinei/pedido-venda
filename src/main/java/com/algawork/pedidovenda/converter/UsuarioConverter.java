package com.algawork.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algawork.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.utils.cdi.CDIServiceLocator;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	// @Inject
	private Usuarios usuarios;

	public UsuarioConverter() {
		usuarios = CDIServiceLocator.getBean(Usuarios.class);
	}
	
//	 @Override
//	    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
//	        if (value != null && !value.isEmpty()) {
//	            return (Usuario) uiComponent.getAttributes().get(value);
//	        }
//	        return null;
//	    }
//
//	    @Override
//	    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
//	        if (value instanceof Usuario) {
//	        	Usuario entity= (Usuario) value;
//	            if (entity != null && entity instanceof Usuario && entity.getId() != null) {
//	                uiComponent.getAttributes().put( entity.getId().toString(), entity);
//	                return entity.getId().toString();
//	            }
//	        }
//	        return "";
//	    }

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = usuarios.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			Usuario usuario = (Usuario) value;
			if (usuario != null) {
				return usuario.getId() == null ? null : usuario.toString();
			}
		
		}
		return "";
	}

}
