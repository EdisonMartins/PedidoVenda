package com.algaworks.pedidovenda.infrastructure.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

/**
 * Classe responsavel por manter os métodos Genéricos para interação com o Banco
 * de Dados. 
 * 
 * @author Edison Gonçalves Martins Júnior - martinsegmj@gmail.com
 * @version 1.3 - 29/08/2015
 * @since 23/07/2015
 */

public class GenericDAO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Class<T> classe;

	@Inject
	protected EntityManager entityManager;

	// Construtores
	/**
	 * Construtor - EntityManger gerenciado pelo CDI
	 * 
	 * @param classe
	 */
	public GenericDAO() {
		//Busca classe por reflexão
		this.classe  = getClassePorReflexao();

	}


	
	public GenericDAO(EntityManager entityManager) {
		this.classe  = getClassePorReflexao();
		this.entityManager = entityManager;
	}

	
	

	/**
	 * Construtor - EntityManger gerenciado manualmente
	 * 
	 * @param classe
	 * @param manager
	 */
	public GenericDAO(Class<T> classe, EntityManager biaManager) {
		this.classe = classe;
		this.entityManager = biaManager;
	}
	
	
	@SuppressWarnings("unchecked")
	private Class<T> getClassePorReflexao() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
	            .getActualTypeArguments()[0];
	}
	
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}



	// métodos

	/**
	 * Executa o comando INSERT no Banco de Dados
	 * 
	 * @param t
	 *            - Tipo Gen�rico
	 */
	public void insert(T t) {
		this.entityManager.persist(t);
	}

	/**
	 * Executa o comando UPDATE no Banco de Dados
	 * 
	 * @param t
	 *            - Tipo Gen�rico
	 */
	public void update(T t) {

		this.entityManager.merge(t);
	}

	/**
	 * Executa o comando UPDATE no Banco de Dados
	 * 
	 * @param t
	 *            - Tipo Gen�rico
	 * @return Um objeto gerenciado <T> (Tipo Gen�rico) ap�s ser atualizado.
	 */
	public T merge(T t) {

		return this.entityManager.merge(t);
	}

	/**
	 * Executa o comando DELETE no Banco de Dados
	 * 
	 * @param id
	 *            - Chave prim�ria
	 */
	public void delete(Object id) {
		T entityToBeRemoved = entityManager.getReference(classe, id);

		entityManager.remove(entityToBeRemoved);
	}
	
	/**
	 * Delega o flush para EntityManager
	 */
	public void flush(){
		this.entityManager.flush();
	}
	
	
	/**
	 * Executa o comando DELETE no Banco de Dados
	 * 
	 * @param id
	 *            - Chave prim�ria
	 */
	public void deleteAndFlush(Object id) {
		T entityToBeRemoved = entityManager.getReference(classe, id);

		entityManager.remove(entityToBeRemoved);
		entityManager.flush();
	}
	

	
	

	/**
	 * Executa o comando SELECT no Banco de Dados
	 * 
	 * @param id
	 *            - Um Long que representa uma chave prim�ria
	 * @return Um objeto gerenciado <T> (Tipo Gen�rico) de acordo com o
	 *         argumento passado.
	 */
	public T findById(Long id) {
		return this.entityManager.find(classe, id);
	}

	/**
	 * Executa o comando SELECT no Banco de Dados
	 * 
	 * @param id
	 *            - Um BigDecimal que representa uma chave prim�ria
	 * @return Um objeto gerenciado <T> (Tipo Gen�rico) de acordo com o
	 *         argumento passado.g
	 */
	public T findById(BigDecimal id) {
		System.out.println("ID: " + id);
		return this.entityManager.find(classe, id);
	}

	/**GenericDAO

	 * Executa o comando SELECT no Banco de Dados
	 * 
	 * @param id
	 *            - Uma String que representa uma chave prim�ria
	 * @return Um objeto gerenciado <T> (Tipo Gen�rico) de acordo com o
	 *         argumento passado.
	 */
	public T findById(String id) {
		return this.entityManager.find(classe, id);
	}

	/**
	 * Executa o comando SELECT no Banco de Dados
	 * 
	 * @param id
	 *            - Uma int que representa uma chave prim�ria
	 * @return Um objeto gerenciado <T> (Tipo Gen�rico) de acordo com o
	 *         argumento passado.
	 */
	public T findById(int id) {
		return entityManager.find(classe, id);
	}

	/**
	 * Busca apenas a referencia no banco de dados
	 * 
	 * @param id
	 * @return int - id
	 */
	public T findReferenceOnly(int id) {
		return entityManager.getReference(classe, id);
	}

	/**
	 * Retorna uma lista de objetos.
	 * 
	 * @return Uma lista de objetos gerenciados <T>.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
		cq.select(cq.from(classe));
		return entityManager.createQuery(cq).getResultList();
	}

	/**
	 * Executa o comando SELECT no Banco de Dados
	 * 
	 * @param numeroDeLinhas
	 *            - Representa o n�mero de linhas da tabela no banco de dados.
	 * @return Uma lista de objetos gerenciados <T>.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(Integer numeroDeLinhas) {
		Long numeroDeLinhasLong = Long.parseLong(numeroDeLinhas.toString());
		Query query = this.entityManager.createQuery("select x from "
				+ this.classe.getName() + " x " + "where rownum <= ?1");

		query.setParameter(1, numeroDeLinhasLong);
		return query.getResultList();

	}

	/**
	 * Retorna um �nico resultado de acordo com a namedQuery fornecida
	 * 
	 * @param namedQuery
	 * @param parameters
	 * @return
	 */
	// Using the unchecked because JPA does not have a
	// query.getSingleResult()<T> method
	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = this.entityManager.createNamedQuery(namedQuery);

			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			System.out
					.println("No result found for named query: " + namedQuery);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	
	
	
	
	/**
	 * 
	 * @return Quantidade total de registros.
	 */
	public int getQuantidadeTotalDeRegistros(){
		Session session = this.getEntityManager().unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(classe);
		criteria.setProjection(Projections.rowCount());
		
		return  ((Number) criteria.uniqueResult()).intValue();
		
	}
	
	

	/**
	 * M�todo para popular par�metros dinamicamente independente da query
	 * 
	 * @param query
	 * @param parameters
	 */
	private void populateQueryParameters(Query query,
			Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

}
