package com.algaworks.pedidovenda.domain.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.algaworks.pedidovenda.domain.model.ItemPedido;
import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.repository.Pedidos;

public class EstoqueServiceImpl implements Serializable, EstoqueService {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pedidos pedidoRep;

	@Override
	@Transactional
	public void baixarEstoque(Pedido pedido) {
		
		pedido = recuperarPedidoAtual(pedido);
		
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarQuantidade(item.getQuantidade());
		}
		
	}

	@Transactional
	@Override
	public void retornarItensEstoque(Pedido pedido) {
		pedido = recuperarPedidoAtual(pedido);
		
		for(ItemPedido item : pedido.getItens()){
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}
		
		
	}
	
	

	private Pedido recuperarPedidoAtual(Pedido pedido) {
		return this.pedidoRep.porId(pedido.getId());
	}
	
	
	

}
