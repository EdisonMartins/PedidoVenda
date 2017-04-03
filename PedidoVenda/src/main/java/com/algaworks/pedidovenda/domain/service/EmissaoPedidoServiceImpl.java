package com.algaworks.pedidovenda.domain.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.StatusPedido;
import com.algaworks.pedidovenda.domain.service.exception.NegocioExceptionImpl;
import com.algaworks.pedidovenda.infrastructure.dao.PedidoDAO;
import com.algaworks.pedidovenda.infrastructure.util.jpa.Transactional;

public class EmissaoPedidoServiceImpl implements EmissaoPedidoService, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	@Inject
	private EstoqueService estoqueService;

	@Inject
	private PedidoDAO pedidoDAO;

	@Transactional
	@Override
	public Pedido emitir(Pedido pedido) {
		pedido = cadastroPedidoService.salvar(pedido);

		if (pedido.isNaoEmissivel()) {
			throw new NegocioExceptionImpl("Pedido não pode ser emitido com status "
					+ pedido.getStatus().getDescricao() + ".");
		}
		
		this.estoqueService.baixarEstoque(pedido);

		pedido.setStatus(StatusPedido.EMITIDO);

		pedido = this.pedidoDAO.merge(pedido);

		return pedido;

	}
}
