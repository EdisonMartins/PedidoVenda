package com.algaworks.pedidovenda.domain.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.domain.model.Produto;
import com.algaworks.pedidovenda.domain.model.repository.ProdutoRepository;
import com.algaworks.pedidovenda.domain.service.exception.NegocioExceptionImpl;
import com.algaworks.pedidovenda.infrastructure.util.jpa.Transactional;

public class CadastroProdutoServiceImpl implements Serializable, CadastroProdutoService {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtos;

	
	@Transactional
	@Override
	public Produto salvar(Produto produto) {
		Produto produdoExistente = produtos.porSku(produto.getSku());

		//Validação que permite editar um determinado produto.
		if (produdoExistente != null && !produdoExistente.equals(produto)) {
			throw new NegocioExceptionImpl("Já existe um produto com o SKU informado.");
		}

		return produtos.salva(produto);
	}
	
	
	@Transactional
	@Override
	public void remover(Produto produto) {
		try {
			produto = produtos.porId(produto.getId());
			produtos.deleteAndFlush(produto.getId());
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
