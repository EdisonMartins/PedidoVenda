package com.algaworks.pedidovenda.application.controller.component;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.UsuarioRepositoryImpl;
import com.algaworks.pedidovenda.domain.model.repository.dto.UsuarioFilter;

public class LazyUsuarioDataModel extends LazyDataModel<Usuario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UsuarioFilter filtro;

	private List<Usuario> lista;

	private UsuarioRepositoryImpl usuarios;

	public LazyUsuarioDataModel(UsuarioRepositoryImpl usuarios, UsuarioFilter filtro) {
		this.usuarios = usuarios;
		this.filtro = filtro;

	}

	@Override
	public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		System.out.println("First: " + first);
		System.out.println("PageSize " + pageSize);
		System.out.println("SortField: " + sortField);
		System.out.println("SortOrder: " + sortOrder);

		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistro(pageSize);

		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));

		setRowCount(usuarios.getQuantidadeFiltrados(filtro));

		this.lista = usuarios.filtrados(filtro);

		return this.lista;
	}

}
