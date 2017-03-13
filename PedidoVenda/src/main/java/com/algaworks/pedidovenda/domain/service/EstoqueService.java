package com.algaworks.pedidovenda.domain.service;

import com.algaworks.pedidovenda.domain.model.Pedido;

public interface EstoqueService {

	void baixarEstoque(Pedido pedido);

	void retornarItensEstoque(Pedido pedido);

}
