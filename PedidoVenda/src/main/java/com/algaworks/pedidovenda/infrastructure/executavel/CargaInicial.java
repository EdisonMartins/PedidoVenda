package com.algaworks.pedidovenda.infrastructure.executavel;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.algaworks.pedidovenda.domain.model.Categoria;
import com.algaworks.pedidovenda.domain.model.Grupo;
import com.algaworks.pedidovenda.domain.model.Produto;

public class CargaInicial {
	public static void main(String[] args) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager manager = factory.createEntityManager();

		EntityTransaction trx = manager.getTransaction();
		trx.begin();

		// instanciamos a categoria pai (Bebidas)
		Categoria categoriaPai = new Categoria();
		categoriaPai.setDescricao("Bebidas");

		// instanciamos a categoria filha (Refrigerantes)
		Categoria categoriaFilha = new Categoria();
		categoriaFilha.setDescricao("Refrigerantes");
		categoriaFilha.setCategoriaPai(categoriaPai);

		// adicionamos a categoria Refrigerantes como filha de Bebidas

		categoriaPai.getSubcategorias().add(categoriaFilha);

		// ao persistir a categoria pai (Refrigerantes), a filha (Bebidas)
		// deve ser persistida também
		manager.persist(categoriaPai);

		// instanciamos e persistimos um produto
		Produto produto = new Produto();
		produto.setCategoria(categoriaFilha);
		produto.setNome("Guaraná 2L");
		produto.setQuantidadeEstoque(10);
		produto.setSku("GU00123");
		produto.setValorUnitario(new BigDecimal("2.21"));

		manager.persist(produto);

		Grupo grupo = new Grupo("Auxiliares", "Auxilia os vendedores nas tarefas.");
		manager.persist(grupo);

		grupo = new Grupo("Administradores", "Administra o setor.");
		manager.persist(grupo);
		
		
		grupo = new Grupo("Vendedores", "Responsável pela venda dos produtos.");
		manager.persist(grupo);

		trx.commit();

		manager.close();
		factory.close();

	}
}
