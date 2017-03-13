package com.algaworks.pedidovenda.domain.model.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.repository.dto.PedidoFilter;
import com.algaworks.pedidovenda.infrastructure.dao.PedidoDAO;

public class PedidoRepositoryImpl implements PedidoRepository, Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private PedidoDAO pedidoDAO;

	@Override
	public Pedido porId(Long id) {
		return pedidoDAO.findById(id);
	}

	@Override
	public List<Pedido> filtrados(PedidoFilter filtro) {
		return pedidoDAO.filtrados(filtro);
	}

	@Override
	public int getQuantidadeFiltrados(PedidoFilter filtro) {
		return pedidoDAO.getQuantidadeFiltrados(filtro);
	}

}
