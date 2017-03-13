package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.Cliente;
import com.algaworks.pedidovenda.infrastructure.util.mail.Mailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioClienteEmailBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Mailer mailer;
	
	public void enviarDadosDoCliente(Cliente cliente){
		MailMessage message = mailer.novaMensagem();
		
		message.to(cliente.getEmail())
		.subject("Dados do Cadastro")
		.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/cliente.template")))
		.put("cliente", cliente)
		.send();
		
		
		FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");	
	}
	

}
