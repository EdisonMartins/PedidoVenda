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
import com.algaworks.pedidovenda.domain.service.CancelamentoPedidoService;

@Named
@RequestScoped
public class CancelamentoPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CancelamentoPedidoService cancelamentoPedidoService;
	
	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	public void cancelarPedido(){
		System.out.println("cancelarPedido()");
		
		this.pedido = this.cancelamentoPedidoService.cancelar(pedido);
		this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(pedido));
		
		FacesUtil.addInfoMessage("Pedido cancelado com sucesso!");
	}

}
