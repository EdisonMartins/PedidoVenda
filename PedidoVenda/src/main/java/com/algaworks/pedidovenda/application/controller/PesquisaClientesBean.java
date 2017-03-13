package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.application.controller.component.LazyClienteDataModel;
import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.Cliente;
import com.algaworks.pedidovenda.domain.model.repository.ClienteRepositoryImpl;
import com.algaworks.pedidovenda.domain.model.repository.dto.ClienteFilter;
import com.algaworks.pedidovenda.domain.service.ClienteService;

@Named
@ViewScoped
public class PesquisaClientesBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//List
	private LazyClienteDataModel model;
	private ClienteFilter filtro;
	
	private Cliente clienteSelecionado;
	
	@Inject
	private ClienteService clienteService;
	 
	
	@Inject
	private ClienteRepositoryImpl clientes;
	
	
	
	@PostConstruct
	public void init(){
		System.out.println("Início de PesquisaClientesBean");
	}

	public PesquisaClientesBean() {
		this.filtro = new ClienteFilter();
	}
	
	
	public void inicializar() {
		System.out.println("inicializar()");

		if (FacesUtil.isNotPostBack()) {
			model = new LazyClienteDataModel(clientes, filtro);
		}

	}

	public LazyClienteDataModel getModel() {
		return model;
	}


	public ClienteFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(ClienteFilter filtro) {
		this.filtro = filtro;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}
	
	public void excluir(){
		System.out.println("Excluir");
		clienteService.excluir(clienteSelecionado);
		FacesUtil.addInfoMessage(clienteSelecionado.getNome() + " excluído(a) com sucesso");
	}

}
