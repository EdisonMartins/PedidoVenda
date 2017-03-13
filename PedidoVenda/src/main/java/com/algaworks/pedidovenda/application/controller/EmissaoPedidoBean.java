package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.application.controller.annotation.PedidoEdicao;
import com.algaworks.pedidovenda.application.controller.event.PedidoAlteradoEvent;
import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.service.EmissaoPedidoService;

@Named
@RequestScoped
public class EmissaoPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmissaoPedidoService emissaoPedidoService;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;
	
	
	public void emitirPedido(){
		System.out.println("emitirPedido()");
		this.pedido.removerItemVazio();
		
		try {
			this.pedido = this.emissaoPedidoService.emitir(this.pedido);
			// lançar um evento CDI
			this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(pedido));
			
			
			FacesUtil.addInfoMessage("Pedido emitido com sucesso!");
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}
	
}
