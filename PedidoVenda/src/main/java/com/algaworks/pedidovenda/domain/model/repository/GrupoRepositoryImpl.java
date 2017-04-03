package com.algaworks.pedidovenda.domain.model.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.algaworks.pedidovenda.domain.model.Grupo;
import com.algaworks.pedidovenda.domain.service.exception.NegocioExceptionImpl;
import com.algaworks.pedidovenda.infrastructure.util.jpa.Transactional;

public class GrupoRepositoryImpl implements Serializable, GrupoRepository {

	private static final long serialVersionUID = 1L;

	// EntityManager
	@Inject
	private EntityManager manager;

	// Método que Guarda objeto no banco
	@Inject
	public Grupo guardar(Grupo grupo) {

		grupo = manager.merge(grupo);

		return grupo;
	}

	// Buscar pelo ID
	@Override
	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}

	// Busca pela Business ID. Tem que ser Unique
	@Override
	public Grupo porNome(String nome) {

		try {
			return manager.createQuery("from Grupo where nome = :nome", Grupo.class).setParameter("nome", nome)
					.getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}

	}

	@Override
	public List<Grupo> raizes() {
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}

	// Método para remoção
	@Transactional
	@Override
	public void remover(Grupo grupo) {
		try {
			grupo = porId(grupo.getId());
			manager.remove(grupo);
			// Chamando flush para excluir imediatamente o Grupo e se ele
			// estiver sendo
			// usado por Pedido, o flush() irá lançar um exceção neste momento e
			// não no @Transactional.
			manager.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioExceptionImpl("Grupo não pode ser excluído");
		}
	}

}
