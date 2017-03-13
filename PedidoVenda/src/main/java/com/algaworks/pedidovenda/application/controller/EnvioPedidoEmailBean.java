package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.algaworks.pedidovenda.application.controller.annotation.PedidoEdicao;
import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.infrastructure.util.mail.Mailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioPedidoEmailBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Mailer mailer;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	public void enviarPedido(){
		MailMessage message = mailer.novaMensagem();
		
		message.to(this.pedido.getCliente().getEmail())
		.subject("Pedido " + this.pedido.getId())
		.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/pedido.template")))
		.put("pedido", this.pedido)
		.put("numberTool", new NumberTool())
		.put("locale", new Locale("pt", "BR"))
		.send();
		
/*		message.to("martinsegmj@gmail.com")
		.subject("Pedido " + this.pedido.getId())
		.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/pedido.template")))
		.put("pedido", this.pedido)
		.put("numberTool", new NumberTool())
		.put("locale", new Locale("pt", "BR"))
		.send();*/
		
		
		
/*		message.to("martinsegmj@gmail.com")
		.subject("Pedido " + this.pedido.getId())
		.bodyHtml("<strong>Valor total:</strong>" + this.pedido.getValorTotal())
		.charset("UTF-8") 
		.send();*/
		
		FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");	
	}

}
