package com.algaworks.pedidovenda.domain.service;

import com.algaworks.pedidovenda.domain.model.Pedido;

public interface EmissaoPedidoService {

	Pedido emitir(Pedido pedido);

}
