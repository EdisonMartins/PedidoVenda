package com.algaworks.pedidovenda.infrastructure.teste;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.algaworks.pedidovenda.domain.model.Categoria;
import com.algaworks.pedidovenda.domain.model.CategoriaInfo;
import com.algaworks.pedidovenda.domain.model.Produto;
import com.algaworks.pedidovenda.infrastructure.util.jpa.JPAUtil;

public class TestaCriteria {

	public static void main(String[] args) {

		/*
		 * select c.descricao, count(p.id) as QTD_PRODUTOS from produto p left
		 * join categoria c on c.id = p.categoria_id group by categoria_id
		 */

		EntityManager em = JPAUtil.createEntityManager();

		Session session = (Session) em;
		Criteria criteria = session.createCriteria(Produto.class);

		//Lista de projeções
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("nome"))
				.add(Projections.count("id"))
				.add(Projections.groupProperty("categoria")));
		
		//criteria.add(Restrictions.ge("quantidadeEstoque", 8));

		@SuppressWarnings("unchecked")
		List<Object[]> resultados = criteria.list();

		List<CategoriaInfo> categoriasInfo = new ArrayList<CategoriaInfo>();
		for (Object[] obj : resultados) {
			String nome = (String) obj[0];
			System.out.println(nome);
			
			Long count = (Long) obj[1];
			
			Integer quantidadeDeProdutos = Integer.valueOf(count.toString());
			
			System.out.println(count);
			
		
			Categoria categoria = (Categoria) obj[2];
			System.out.println(categoria);
			
			
			categoriasInfo.add(new CategoriaInfo(categoria.getDescricao(), quantidadeDeProdutos));
			
		}
		
		
		
		for (CategoriaInfo categoriaInfo : categoriasInfo) {
			System.out.println(categoriaInfo);
		}
		
		

	}

}
