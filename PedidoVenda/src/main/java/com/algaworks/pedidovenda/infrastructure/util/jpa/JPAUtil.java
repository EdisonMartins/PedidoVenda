package com.algaworks.pedidovenda.infrastructure.util.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PedidoPU");
	
	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}

}
