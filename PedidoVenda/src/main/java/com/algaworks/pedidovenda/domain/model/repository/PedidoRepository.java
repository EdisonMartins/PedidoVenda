package com.algaworks.pedidovenda.domain.model.repository;

import java.util.List;

import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.repository.dto.PedidoFilter;

public interface PedidoRepository extends GenericRepository<Pedido>{
	public List<Pedido> filtrados(PedidoFilter filtro);
	public int getQuantidadeFiltrados(PedidoFilter filtro);
}
