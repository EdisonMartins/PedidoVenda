package com.algaworks.pedidovenda.domain.service;

import com.algaworks.pedidovenda.domain.model.Cliente;
import com.algaworks.pedidovenda.domain.model.Endereco;

public interface ClienteService extends GenericService<Cliente> {
	public void excluir(Cliente clienteSelecionado);

	public void removerEndereco(Cliente cliente, Endereco endereco);
}
