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

import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.dto.UsuarioFilter;

public class UsuarioRepositoryImpl implements Serializable, Usuarios {

	private static final long serialVersionUID = 1L;

	// EntityManager
	@Inject
	private EntityManager manager;

	public UsuarioRepositoryImpl() {

	}

	public UsuarioRepositoryImpl(EntityManager manager) {
		this.manager = manager;
	}

	// Método que Guarda objeto no banco
	@Override
	public Usuario guardar(Usuario usuario) {

		usuario = manager.merge(usuario);

		return usuario;
	}

	// Buscar pelo ID
	@Override
	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

	// Busca pela Business ID. Tem que ser Unique
	@Override
	public Usuario porEmail(String email) {

		try {
			return manager.createQuery("from Usuario where email = :email", Usuario.class).setParameter("email", email)
					.getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}

	}
	
	@Override
	public List<Usuario> getVendedores(){
		// TODO Mudar a query depois para saber quando o Usuário é vendedor.
		return this.manager.createQuery("from Usuario", Usuario.class).getResultList();
	}
	

	// Método para remoção
	@Override
	public void remover(Usuario usuario) {
		usuario = porId(usuario.getId());
		manager.remove(usuario);
		// Chamando flush para excluir imediatamente o Usuario e se ele
		// estiver sendo
		// usado por Pedido, o flush() irá lançar um exceção neste momento e
		// não no @Transactional.
		manager.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> filtrados(UsuarioFilter filtro) {
		Criteria criteria = criarCriteriaPara(filtro);
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistro());
		return criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao())).list();
	}

	@Override
	public int getQuantidadeFiltrados(UsuarioFilter filtro) {
		Criteria criteria = criarCriteriaPara(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();

	}

	private Criteria criarCriteriaPara(UsuarioFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);

		System.out.println("Nome filtrado: " + filtro.getNome());

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		return criteria;
	}

}
