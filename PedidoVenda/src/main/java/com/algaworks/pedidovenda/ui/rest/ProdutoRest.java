package com.algaworks.pedidovenda.ui.rest;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.algaworks.pedidovenda.domain.model.Cliente;
import com.algaworks.pedidovenda.domain.model.Produto;
import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.UsuarioRepositoryImpl;

@Path("/rest/produto")
@RequestScoped
public class ProdutoRest {
	
	@Inject
	private UsuarioRepositoryImpl usuarioRep;
	
	public ProdutoRest(){
		System.out.println("ProdutoRest()");
	}
	@GET
	@Path("ola")
	public String ola(){
		System.out.println("/ola");
		
		return "ola";
		
		/*
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		trx.begin();

		Produto produto = manager.find(Produto.class, 1L);

		System.out.println(produto.getNome());
		manager.close();
		factory.close();
		
		*/
	}
	
	@GET
	@Path("produtos")
	@Produces({"application/xml"})
	public List<Produto> produtos(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		
		
		List<Produto> produtos = new ArrayList<Produto>();

		Produto produto = manager.find(Produto.class, 2L);
		
		produtos.add(produto);
	
		
		System.out.println("Produtos: " + produtos.size());

		//System.out.println(produto.getNome());
		manager.close();
		factory.close();
		
		return produtos;
		
	}
	
	
	@GET
	@Path("clientes")
	@Produces({"application/xml"})
	public List<Cliente> clientes(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		
		
		List<Cliente> clientes = new ArrayList<>();

		Cliente c1 = manager.find(Cliente.class, 1L);
		clientes.add(c1);
		
		Cliente c2 = manager.find(Cliente.class, 2L);
		clientes.add(c2);
	
		
		System.out.println("Produtos: " + clientes.size());

		manager.close();
		factory.close();
		
		return clientes;
		
	}
	
	
	@GET
	@Path("usuario")
	@Produces({"application/xml"})
	public Usuario usuarios(){
		
		//Pesquisar sobre JAX-RS com CDI
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager manager = factory.createEntityManager();
		this.usuarioRep = new UsuarioRepositoryImpl(manager);
		
		Usuario u = this.usuarioRep.porId(1L);
		System.out.println(u);
		
		return u;
	}
	
	
	@GET
	@Path("usuario-com-cdi")
	@Produces({"application/xml"})
	public Usuario usuariosComCDI(){
		
		
		
		Usuario u = this.usuarioRep.porId(1L);
		System.out.println(u);
		
		return u;
	}

}
