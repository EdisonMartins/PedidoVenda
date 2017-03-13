package com.algaworks.pedidovenda.infrastructure.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.repository.dto.PedidoFilter;

public class PedidoDAO extends GenericDAO<Pedido> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoDAO() {
		super();
	}

	public int getQuantidadeFiltrados(PedidoFilter filtro) {

		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());

		return ((Number) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> filtrados(PedidoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistro());

		// Join com cliente
		criteria.createAlias("cliente", "c");
		// Join com vendedor
		criteria.createAlias("vendedor", "v");

		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {

			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));

		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}

		return criteria.list();
	}

	private Criteria criarCriteriaParaFiltro(PedidoFilter filtro) {

		Session session = this.entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Pedido.class);
  
		if (filtro.getNumeroDe() != null) {
			// Id deve ser maior ou igual (ge = greater or equals)
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {

			// Id deve ser menor ou igual (le = lower or equal)
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		}

		if (filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		}

		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			// Acessamos o nome do cliente associado ao pedido pelo alias "c",
			// criado anteriormente.
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeVendedor())) {
			// Acessamos o nome do vendedor associado ao pedido pelo alias "v",
			// criado anteriormente.
			criteria.add(Restrictions.ilike("v.nome", filtro.getNomeVendedor(), MatchMode.ANYWHERE));
		}

		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// Adicionamos uma restrição "in", passando um array de constantes
			// de enum StatusPedido
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}

		return criteria;

	}

}
