package com.algaworks.pedidovenda.domain.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.domain.model.Grupo;
import com.algaworks.pedidovenda.domain.model.repository.GrupoRepository;
import com.algaworks.pedidovenda.domain.service.exception.NegocioExceptionImpl;
import com.algaworks.pedidovenda.infrastructure.util.jpa.Transactional;

public class GrupoServiceImpl implements Serializable, GrupoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	@Inject
	private GrupoRepository grupos;

	@Transactional
	@Override
	public Grupo salvar(Grupo grupo) {
		Grupo grupoExistente = grupos.porNome(grupo.getNome());

		//Validação que permite editar um determinado grupo.
		if (grupoExistente != null && !grupoExistente.equals(grupo)) {
			throw new NegocioExceptionImpl("Já existe um grupo com este nome.");
		}

		return grupos.guardar(grupo);
	}

}
