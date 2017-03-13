package com.algaworks.pedidovenda.infrastructure.dao;

import java.util.List;

import com.algaworks.pedidovenda.domain.model.Categoria;

/**
 * Classe que acessa de fato o banco de dados. Padrão DAO.
 * 
 * @author Edison Gonçalves Martins Júnior - martinsegmj@gmail.com
 * @version 1.0 - 23/07/2015
 * @since 23/07/2015
 *
 */
public class CategoriaDAO extends GenericDAO<Categoria> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoriaDAO() {
		super();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Categoria> getCategoriasSemPai() {
		return this.getEntityManager().createNamedQuery(Categoria.CATEGORIAS_SEM_PAI).getResultList();

	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> getSubcategoriasDe(Categoria categoriaPai) {
		return this.getEntityManager().createNamedQuery(Categoria.SUBCATEGORIAS)
				.setParameter("raiz", categoriaPai).getResultList();

	}

}
