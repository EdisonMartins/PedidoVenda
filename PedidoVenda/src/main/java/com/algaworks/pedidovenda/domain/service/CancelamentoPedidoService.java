package com.algaworks.pedidovenda.domain.service;

import com.algaworks.pedidovenda.domain.model.Pedido;

public interface CancelamentoPedidoService {

	Pedido cancelar(Pedido pedido);

}
