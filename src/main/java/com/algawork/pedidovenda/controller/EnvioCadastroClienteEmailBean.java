package com.algawork.pedidovenda.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.algawork.pedidovenda.util.FormatadorUtil;
import com.algawork.pedidovenda.util.mail.Mailer;
import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioCadastroClienteEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Mailer mailer;
	
	@Inject
	@ClienteEdicao
	private Cliente cliente;
	
	 @Inject
	private Event<ClienteAlteradoEvent> clienteAlteradoEvent;
	   
	public void enviarCadastro() throws ParseException {
		MailMessage message = mailer.novaMensagem();
		this.clienteAlteradoEvent.fire(new ClienteAlteradoEvent(this.cliente));
		
		message.to(new String[] {this.cliente.getEmail()})
			.subject("Seu Cadastro")
			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/cliente.template")))
			.put("cliente", this.cliente)
			.put("cpfOrCnpj", retornaFormatado(this.cliente.getTipo().toString()))
			.put("numberTool", new NumberTool())
			.put("locale", new Locale("pt", "BR"))
			.send();
				
		FacesUtil.addInfoMessage("Cadastro enviado por e-mail com sucesso!");

	}

	
	private String retornaFormatado(String tipoPessoa) throws ParseException{
		String txtFormatado = null;
		
		if(tipoPessoa.equals("F")){
			txtFormatado = FormatadorUtil.formatarString(this.cliente.getDocumentoReceitaFederal(),"###.###.###-##");	
			
		}else if(tipoPessoa.equals("J")){
			txtFormatado = FormatadorUtil.formatarString(this.cliente.getDocumentoReceitaFederal(),"##.###.###/####-##");
			
		}
		
		return txtFormatado;
		
	}
	
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	

}
