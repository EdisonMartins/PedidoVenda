package com.algaworks.pedidovenda.domain.model.repository;

import java.util.List;

import com.algaworks.pedidovenda.domain.model.Produto;
import com.algaworks.pedidovenda.domain.model.repository.dto.ProdutoFilter;

public interface ProdutoRepository extends GenericRepository<Produto> {
	
	public Produto porSku(String sku);
	
	public List<Produto> filtrados(ProdutoFilter filtro);

	public List<Produto> porNome(String nome);

}
