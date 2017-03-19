package com.algaworks.pedidovenda.domain.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.StatusPedido;
import com.algaworks.pedidovenda.domain.model.repository.Pedidos;
import com.algaworks.pedidovenda.domain.service.exception.NegocioExceptionImpl;
import com.algaworks.pedidovenda.infrastructure.dao.PedidoDAO;

public class CancelamentoPedidoServiceImpl implements Serializable, CancelamentoPedidoService {

	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private Pedidos pedidoRep;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Inject
	private PedidoDAO pedidoDAO;
	

	@Transactional
	@Override
	public Pedido cancelar(Pedido pedido) {
		pedido = this.pedidoRep.porId(pedido.getId());
		
		if(pedido.isNaoCancelavel()){
			throw new NegocioExceptionImpl("Pedido não pode ser cancelado no status " + pedido.getStatus().getDescricao() + ".");
		}
		
		
		if(pedido.isEmitido()){
			this.estoqueService.retornarItensEstoque(pedido);
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		
		
		pedido = this.pedidoDAO.merge(pedido);
		
		return pedido;
	}

}
