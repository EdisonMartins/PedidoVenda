package com.algaworks.pedidovenda.domain.model.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.domain.model.Cliente;
import com.algaworks.pedidovenda.domain.model.repository.dto.ClienteFilter;

public class ClienteRepositoryImpl implements Serializable, Clientes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// EntityManager
	@Inject
	private EntityManager manager;

	// Buscar pelo ID
	@Override
	public Cliente porId(Long id) {
		return manager.find(Cliente.class, id);
	}

	// Método que Guarda objeto no banco
	@Override
	public Cliente guardar(Cliente cliente) {

		cliente = manager.merge(cliente);

		return cliente;
	}

	// Busca pela Business ID. Tem que ser Unique
	@Override
	public Cliente porEmail(String email) {

		try {
			return manager.createQuery("from Cliente where email = :email", Cliente.class).setParameter("email", email)
					.getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}

	}

	@Override
	public List<Cliente> porNome(String nome) {
		return manager.createQuery("from Cliente where upper(nome) like :nome", Cliente.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> filtrados(ClienteFilter filtro) {
		Criteria criteria = criarCriteriaPara(filtro);
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistro());
		return criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao())).list();
	}

	@Override
	public int getQuantidadeFiltrados(ClienteFilter filtro) {
		Criteria criteria = criarCriteriaPara(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}

	private Criteria criarCriteriaPara(ClienteFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cliente.class);

		System.out.println("Nome filtrado: " + filtro.getNome());

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getDocumentoReceitaFederal())) {
			criteria.add(Restrictions.ilike("documentoReceitaFederal", filtro.getDocumentoReceitaFederal(),
					MatchMode.ANYWHERE));
		}

		return criteria;
	}

	@Override
	public void remover(Cliente clienteSelecionado) {
		Cliente clienteRecuperado = porId(clienteSelecionado.getId());
		manager.remove(clienteRecuperado);
		manager.flush();

	}

}
