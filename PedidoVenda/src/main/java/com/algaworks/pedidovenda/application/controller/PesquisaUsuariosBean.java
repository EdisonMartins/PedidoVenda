package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.application.controller.component.LazyUsuarioDataModel;
import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.UsuarioRepositoryImpl;
import com.algaworks.pedidovenda.domain.model.repository.dto.UsuarioFilter;
import com.algaworks.pedidovenda.domain.service.UsuarioServiceImpl;

@Named
@ViewScoped
public class PesquisaUsuariosBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Componentes
	private Usuario usuarioSelecionado;
	private UsuarioFilter filtro;

	// List
	private LazyUsuarioDataModel model;

	// Injetáveis
	@Inject
	private UsuarioRepositoryImpl usuarios;
	
	@Inject
	private UsuarioServiceImpl usuarioService;
	
	
	
	
	
	
	
	
	public PesquisaUsuariosBean(){
		this.filtro = new UsuarioFilter();
	}
	

	public void inicializar() {
		System.out.println("inicializar()");

		if (FacesUtil.isNotPostBack()) {
			System.out.println("usuariosFiltrados = usuarios.lista()");
			model = new LazyUsuarioDataModel(usuarios, filtro);
		}

	}

	// Getters and Setters

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuario) {
		this.usuarioSelecionado = usuario;
	}

	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(UsuarioFilter filtro) {
		this.filtro = filtro;
	}

	public LazyUsuarioDataModel getModel() {
		return model;
	}
	
	
	//Métodos
	public void excluir(){
		System.out.println("Excluir");
		usuarioService.excluir(usuarioSelecionado);
		FacesUtil.addInfoMessage(usuarioSelecionado.getNome() + " excluído com sucesso");
		
		
		
		
	}
	


}
