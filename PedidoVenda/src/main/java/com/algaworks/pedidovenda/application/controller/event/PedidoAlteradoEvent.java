package com.algaworks.pedidovenda.application.controller.event;

import com.algaworks.pedidovenda.domain.model.Pedido;

public class PedidoAlteradoEvent {
	private Pedido pedido;

	public PedidoAlteradoEvent(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return pedido;
	}

}
