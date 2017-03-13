package com.algaworks.pedidovenda.application.controller.component;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.algaworks.pedidovenda.domain.model.Cliente;
import com.algaworks.pedidovenda.domain.model.repository.ClienteRepositoryImpl;
import com.algaworks.pedidovenda.domain.model.repository.dto.ClienteFilter;

public class LazyClienteDataModel extends LazyDataModel<Cliente> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClienteFilter filtro;
	private List<Cliente> lista;

	private ClienteRepositoryImpl clientes;

	public LazyClienteDataModel(ClienteRepositoryImpl clientes, ClienteFilter filtro) {

		this.clientes = clientes;
		this.filtro = filtro;

	}
	
	
	
	@Override
	public List<Cliente> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		System.out.println("First: " + first);
		System.out.println("PageSize " + pageSize);
		System.out.println("SortField: " + sortField);
		System.out.println("SortOrder: " + sortOrder);
		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistro(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		setRowCount(clientes.getQuantidadeFiltrados(filtro));

		this.lista = clientes.filtrados(filtro);

		return this.lista;
		
		
		
		
	}
}
