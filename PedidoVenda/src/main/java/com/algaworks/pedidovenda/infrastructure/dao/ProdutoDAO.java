package com.algaworks.pedidovenda.infrastructure.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.domain.model.Produto;
import com.algaworks.pedidovenda.domain.model.repository.dto.ProdutoFilter;

public class ProdutoDAO extends GenericDAO<Produto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProdutoDAO() {
		super();
	}
	
	
	public Produto getProdutoPor(String sku){
		try {
			
			return (Produto) this.getEntityManager().createNamedQuery(Produto.POR_SKU).
					setParameter("sku", sku.toUpperCase()).getSingleResult();
			
		} catch (NoResultException nre) {
			return null;
		}
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Produto> filtrados(ProdutoFilter filtro) {
		// Session do Hibernate
		Session session = this.entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Produto.class);

		//Restrictions.eq - Valor tem que ser idêntico.
		if (StringUtils.isNotBlank(filtro.getSku())) {
			criteria.add(Restrictions.eq("sku", filtro.getSku()));
		}

		// Where nome like '%joao' Percent -> MatchMode
		// ilike fornece a opcao de MatchMode
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("nome")).list();
	}

	/**
	 * Recupera lista de Produto iniciados com o nome passado por argumento.
	 * @param nome
	 * @return List<Produto> - Lista de Produtos
	 */
	public List<Produto> porNome(String nome) {
		return this.getEntityManager().createQuery("from Produto where upper(nome) like :nome", Produto.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();

	}
	
	

}
