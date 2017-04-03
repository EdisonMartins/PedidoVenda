package com.algaworks.pedidovenda.infrastructure.util.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

@ApplicationScoped
public class EntityManagerProducer {

	private EntityManagerFactory factory;

	public EntityManagerProducer() {
		System.out.println("Levantando fábrica de conexões...");
		factory = Persistence.createEntityManagerFactory("PedidoPU");
	}

	@Produces
	//@Dependent (Gera erro - Commit não era executado)
	@RequestScoped
	public Session createEntityManager() {
		System.out.println("Fornecendo EntityManager...");
		return (Session) factory.createEntityManager();
	}

	public void closeEntityManager(@Disposes Session manager) {
		manager.close();
		System.out.println("Fechando EntityManager...");
	}
}
