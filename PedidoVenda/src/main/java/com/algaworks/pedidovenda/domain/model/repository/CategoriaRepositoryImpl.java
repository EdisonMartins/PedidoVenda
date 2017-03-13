package com.algaworks.pedidovenda.domain.model.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.algaworks.pedidovenda.domain.model.Categoria;
import com.algaworks.pedidovenda.infrastructure.dao.CategoriaDAO;

/**
 * Facade de DAOs necessários para representar um Repositório de Categorias.
 * 
 * @author Edison Gonçalves Martins Júnior - martinsegmj@gmail.com
 * @version 1.0 - 23/07/2015
 * @since 23/07/2015
 *
 */
public class CategoriaRepositoryImpl implements Serializable, CategoriaRepository {

	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaDAO categoriaDAO;

	@Override
	public Categoria porId(Long id) {
		return categoriaDAO.findById(id);
	}

	@Override
	public List<Categoria> raizes() {
		return categoriaDAO.getCategoriasSemPai();
	}

	@Override
	public List<Categoria> subcategoriasDe(Categoria categoriaPai) {
		return categoriaDAO.getSubcategoriasDe(categoriaPai);

	}

}
