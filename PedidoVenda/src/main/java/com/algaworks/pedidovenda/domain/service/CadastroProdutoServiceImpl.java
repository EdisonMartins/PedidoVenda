package com.algaworks.pedidovenda.domain.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.algaworks.pedidovenda.domain.model.Produto;
import com.algaworks.pedidovenda.domain.service.exception.NegocioExceptionImpl;
import com.algaworks.pedidovenda.infrastructure.dao.ProdutoDAO;

public class CadastroProdutoServiceImpl implements Serializable, CadastroProdutoService {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDAO produtoDAO;

	
	@Transactional
	@Override
	public Produto salvar(Produto produto) {
		Produto produdoExistente = produtoDAO.getProdutoPor(produto.getSku());

		//Validação que permite editar um determinado produto.
		if (produdoExistente != null && !produdoExistente.equals(produto)) {
			throw new NegocioExceptionImpl("Já existe um produto com o SKU informado.");
		}

		return produtoDAO.merge(produto);
	}
	
	
	@Transactional
	@Override
	public void remover(Produto produto) {
		try {
			produto = produtoDAO.findById(produto.getId());
			produtoDAO.deleteAndFlush(produto.getId());
			// Chamando flush para excluir imediatamente o produto e se ele
			// estiver sendo
			// usado por Pedido, o flush() irá lançar um exceção neste momento e
			// não no @Transactional.
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioExceptionImpl("Produto não pode ser excluído");
		}
	}

}
