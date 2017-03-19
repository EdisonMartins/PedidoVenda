package com.algaworks.pedidovenda.domain.model.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.dto.PedidoFilter;

public interface Pedidos extends GenericRepository<Pedido>{
	public List<Pedido> filtrados(PedidoFilter filtro);
	public int getQuantidadeFiltrados(PedidoFilter filtro);
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias, Usuario criadoPor);
}
