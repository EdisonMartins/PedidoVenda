package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.Cliente;
import com.algaworks.pedidovenda.domain.model.Endereco;
import com.algaworks.pedidovenda.domain.model.TipoPessoa;
import com.algaworks.pedidovenda.domain.service.ClienteService;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cliente cliente;
	private TipoPessoa tipoSelecionado;
	private String tipo = new String();
	private Endereco endereco = new Endereco();

	// Service
	@Inject
	private ClienteService clienteService;

	public CadastroClienteBean() {
		cliente = new Cliente();

	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public TipoPessoa getTipoSelecionado() {
		return tipoSelecionado;
	}

	public void setTipoSelecionado(TipoPessoa tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	// Métodos
	public void salvar() {
		System.out.println("Salvar");

		cliente = clienteService.salvar(cliente);

		System.out.println(cliente);

		FacesUtil.addInfoMessage("Cliente " + cliente.getNome() + " salvo com sucesso.");

	}

	private void atualizaBean() {
		this.cliente = new Cliente();

	}

	public void adicionarEndereco() {
		FacesUtil.addInfoMessage("Funcianalidade não implementada!");
	}

	public List<SelectItem> getTiposPessoa() {

		List<SelectItem> tiposPessoa = new ArrayList<SelectItem>();

		tiposPessoa.add(new SelectItem(TipoPessoa.FISICA, TipoPessoa.FISICA.getNome()));
		tiposPessoa.add(new SelectItem(TipoPessoa.JURIDICA, TipoPessoa.JURIDICA.getNome()));

		return tiposPessoa;
	}

	public void incluirEndereco() {

		try {
			cliente.addEndereco(endereco);
			endereco.setCliente(cliente);
			cliente = clienteService.salvar(cliente);
			FacesUtil.addInfoMessage("Endereço incluído com sucesso!");

		} finally {
			this.endereco = new Endereco();
		}

	}
	
	
	
	
	public void excluirEndereco(){
		
		clienteService.removerEndereco(cliente, endereco);
		

		FacesUtil.addInfoMessage("Endereço removido com sucesso!");
		
		this.endereco = new Endereco();
		
	}
	
	
	
	
	

}
