package com.algaworks.pedidovenda.domain.model.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.dto.PedidoFilter;
import com.algaworks.pedidovenda.domain.model.vo.VendedorValor;
import com.algaworks.pedidovenda.infrastructure.dao.PedidoDAO;

public class PedidoRepositoryImpl implements Pedidos, Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private PedidoDAO pedidoDAO;
	
	@Inject
	private Usuarios usuarios;

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

	@Override
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias, Usuario criadoPor) {
		return pedidoDAO.valoresTotaisPorData(numeroDeDias, criadoPor);
	}

	@Override
	public Map<Usuario, BigDecimal> valoresTotaisPorVendedor() {
		List<Usuario> vendedores = usuarios.getVendedores();
		Map<Usuario, BigDecimal> mapaVendedorValor = criaMapaVazio(vendedores);
		List<VendedorValor> valoresPorVendedor = pedidoDAO.valoresTotaisPorVendedor();		
		//Colocar os valores no Mapa
		valoresPorVendedor.forEach((v) -> mapaVendedorValor.put(v.getVendedor(), v.getValor()));
		return mapaVendedorValor;
	}
	
	private Map<Usuario, BigDecimal> criaMapaVazio(List<Usuario> vendedores) {	
		Map<Usuario, BigDecimal> mapaInicial = new TreeMap<>();
		vendedores.forEach((v) -> mapaInicial.put(v, BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP)));
		return mapaInicial;
	}
	
	
	

}
