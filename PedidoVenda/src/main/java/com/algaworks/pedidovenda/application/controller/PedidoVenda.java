package com.algaworks.pedidovenda.application.controller;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.algaworks.pedidovenda.ui.rest.ProdutoRest;

@ApplicationPath("/root-path")
public class PedidoVenda extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public PedidoVenda() {
		// ADD YOUR RESTFUL RESOURCES HERE
		System.out.println("Testando RESTEasy");
		this.singletons.add(new ProdutoRest());
	}

	public Set<Class<?>> getClasses() {
		return this.empty;
	}

	public Set<Object> getSingletons() {
		return this.singletons;
	}
}
