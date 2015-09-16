package com.algawork.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algawork.pedidovenda.repository.Grupos;
import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.utils.cdi.CDIServiceLocator;

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter
{
//	@Inject Inject não funciona com conversores
	private Grupos grupos;

	//solucao
	public GrupoConverter()
	{
		grupos =  CDIServiceLocator.getBean(Grupos.class);
	}

	public Object getAsObject(FacesContext context, UIComponent component, String value) 
	{
		Grupo retorno = null;
		
		if(value != null)
		{
			String nome = new String(value);
			retorno = grupos.grupoPorNome(nome);	
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value != null)
		{
			Grupo grupo = (Grupo) value;
			return grupo.getNome().toString();
		}
		return "";
	}

}