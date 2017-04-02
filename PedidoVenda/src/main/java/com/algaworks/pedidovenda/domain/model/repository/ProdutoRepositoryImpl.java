package com.algaworks.pedidovenda.domain.model.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.algaworks.pedidovenda.domain.model.Produto;
import com.algaworks.pedidovenda.domain.model.repository.dto.ProdutoFilter;
import com.algaworks.pedidovenda.infrastructure.dao.ProdutoDAO;

public class ProdutoRepositoryImpl implements Serializable, ProdutoRepository {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDAO produtoDAO;

	

	@Override
	public Produto porSku(String sku) {

		return produtoDAO.getProdutoPor(sku);
	}


	@Override
	public Produto porId(Long id) {
		return produtoDAO.findById(id);
	}

	@Override
	public List<Produto> filtrados(ProdutoFilter filtro) {
		return produtoDAO.filtrados(filtro);
	}

	@Override
	public List<Produto> porNome(String nome) {
		return produtoDAO.porNome(nome);

	}


	@Override
	public Produto salva(Produto produto) {
		produto = produtoDAO.merge(produto);
		return produto;
	}


	@Override
	public void deleteAndFlush(Long id) {
		produtoDAO.deleteAndFlush(id);
		
	}



}