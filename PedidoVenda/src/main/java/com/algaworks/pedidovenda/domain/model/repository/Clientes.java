package com.algaworks.pedidovenda.domain.model.repository;

import java.util.List;

import com.algaworks.pedidovenda.domain.model.Cliente;
import com.algaworks.pedidovenda.domain.model.repository.dto.ClienteFilter;

public interface Clientes extends GenericRepository<Cliente> {
	public Cliente guardar(Cliente cliente);
	public Cliente porEmail(String email);
	public List<Cliente> filtrados(ClienteFilter filtro);
	public int getQuantidadeFiltrados(ClienteFilter filtro);
	public void remover(Cliente clienteSelecionado);
	public List<Cliente> porNome(String nome);

}
