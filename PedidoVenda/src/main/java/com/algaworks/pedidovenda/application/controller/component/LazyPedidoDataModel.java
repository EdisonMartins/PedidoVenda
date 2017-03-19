package com.algaworks.pedidovenda.application.controller.component;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.repository.Pedidos;
import com.algaworks.pedidovenda.domain.model.repository.dto.PedidoFilter;

public class LazyPedidoDataModel extends LazyDataModel<Pedido> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private PedidoFilter filtro;

	private List<Pedido> lista;

	private Pedidos pedidoRep;

	public LazyPedidoDataModel(Pedidos pedidoRep, PedidoFilter filtro) {
		this.pedidoRep = pedidoRep;
		this.filtro = filtro;

	}

	@Override
	public List<Pedido> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		System.out.println("First: " + first);
		System.out.println("PageSize " + pageSize);
		System.out.println("SortField: " + sortField);
		System.out.println("SortOrder: " + sortOrder);
		
		System.out.println("pedidoRep: " + this.pedidoRep);

		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistro(pageSize);

		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);
		
		
		

		setRowCount(pedidoRep.getQuantidadeFiltrados(filtro));

		this.lista = pedidoRep.filtrados(filtro);
		
		
		System.out.println("Filtro: " + filtro);
 
		return this.lista;
	}

}
