package com.algaworks.pedidovenda.infrastructure.util.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

	private EntityManagerFactory factory;

	public EntityManagerProducer() {
		System.out.println("Levantando fábrica de conexões...");
		factory = Persistence.createEntityManagerFactory("PedidoPU");
	}

	@Produces
	@Dependent
	//@RequestScoped
	public EntityManager createEntityManager() {
		System.out.println("Fornecendo EntityManager...");
		return factory.createEntityManager();
	}

	public void closeEntityManager(@Disposes EntityManager manager) {
		manager.close();
		System.out.println("Fechando EntityManager...");
	}
}
