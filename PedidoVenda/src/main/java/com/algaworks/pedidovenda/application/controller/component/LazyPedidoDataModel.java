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
	
	private int quantidadeFiltrados;

	public LazyPedidoDataModel(Pedidos pedidoRep, PedidoFilter filtro) {
		this.pedidoRep = pedidoRep;
		this.filtro = filtro;
	}



	@Override
	public List<Pedido> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistro(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);		
		setRowCount(pedidoRep.getQuantidadeFiltrados(filtro));
		this.lista = pedidoRep.filtrados(filtro);
		quantidadeFiltrados = this.lista.size(); 
		return this.lista;
	}

	public int getQuantidadeFiltrados() {
		return quantidadeFiltrados;
	}

	public List<Pedido> getLista() {
		return lista;
	}

	public boolean isEmpty() {
		int quantidade = pedidoRep.getQuantidadeFiltrados(filtro);
		if(quantidade == 0){
			return true;
		} else 
			return false;
	}


	
	
	
	
	
}
