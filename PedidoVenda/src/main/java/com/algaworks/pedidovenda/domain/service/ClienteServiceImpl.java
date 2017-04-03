package com.algaworks.pedidovenda.domain.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.domain.model.Cliente;
import com.algaworks.pedidovenda.domain.model.Endereco;
import com.algaworks.pedidovenda.domain.model.repository.Clientes;
import com.algaworks.pedidovenda.domain.service.exception.NegocioExceptionImpl;
import com.algaworks.pedidovenda.infrastructure.util.jpa.Transactional;

public class ClienteServiceImpl implements Serializable, ClienteService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private Clientes clientes;
	
	
	@Override
	@Transactional
	public Cliente salvar(Cliente cliente) {

		Cliente clienteExistente = clientes.porEmail(cliente.getEmail());

		// Validação que permite editar um determinado Usuario.
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioExceptionImpl("Já existe um Cliente com o e-Mail informado.");
		}
		
		

		return clientes.guardar(cliente);
	}

	@Transactional
	@Override
	public void excluir(Cliente clienteSelecionado) {
		try {
			clientes.remover(clienteSelecionado);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioExceptionImpl("Cliente não pode ser excluído");
		}
		
	}

	@Transactional
	@Override
	public void removerEndereco(Cliente cliente, Endereco endereco) {
		cliente.removeEndereco(endereco);
		endereco.setCliente(null);
		
		clientes.guardar(cliente);
		
	}
	
	
}
