package com.algaworks.pedidovenda.domain.service;

import com.algaworks.pedidovenda.domain.model.Produto;

public interface CadastroProdutoService extends GenericService<Produto> {
	public void remover(Produto produto);
}
