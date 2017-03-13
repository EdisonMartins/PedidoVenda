package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.Grupo;
import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.GrupoRepositoryImpl;
import com.algaworks.pedidovenda.domain.model.repository.dto.UsuarioFilter;
import com.algaworks.pedidovenda.domain.service.UsuarioService;
import com.algaworks.pedidovenda.domain.service.exception.NegocioExceptionImpl;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Componentes
	private Usuario usuario;
	private UsuarioFilter filtro;
	private Grupo grupo;
	private Grupo grupoSelecionado;

	// List
	private List<Grupo> gruposRaizes;
	private List<Grupo> gruposAdicionados = new ArrayList<Grupo>();

	// Injetáveis
	@Inject
	private UsuarioService usuarioService;

	@Inject
	private GrupoRepositoryImpl grupos;

	public CadastroUsuarioBean() {
		System.out.println("CadastroUsuarioBean() ");
		
		
		this.usuario = new Usuario();
	}

	public void inicializar() {
		

		
		System.out.println("inicializar()");

		if (FacesUtil.isNotPostBack()) {
			System.out.println("usuariosFiltrados = usuarios.lista()");
			this.gruposRaizes = this.grupos.raizes();
			this.gruposAdicionados.addAll(usuario.getGrupos());
		}
		
		System.out.println("Grupos: " + usuario.getGrupos());

	}

	// Getters and Setters

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(UsuarioFilter filtro) {
		this.filtro = filtro;
	}

	public List<Grupo> getGruposRaizes() {

		return gruposRaizes;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public List<Grupo> getGruposAdicionados() {
		return gruposAdicionados;
	}

	// Métodos
	public void salvar() {
		System.out.println("salvar()");
		
		usuario.getGrupos().clear();
		usuario.getGrupos().addAll(gruposAdicionados);
		
		usuarioService.salvar(usuario);
		FacesUtil.addInfoMessage("Usuário " + usuario.getNome() + " salvo com sucesso.");
		atualizaBean();

	}
	
	
	
	
	public void adicionarGrupo(){
		System.out.println("Adicionar Grupo");

		if(this.grupo == null){
			throw new NegocioExceptionImpl("Selecione um grupo!");
		}
		
		
		if (!gruposAdicionados.contains(grupo)) {
			
			
			gruposAdicionados.add(grupo);
			
			
			

		} else {
			throw new NegocioExceptionImpl("Grupo " + grupo.getNome() + " já adicionado");
		}

		
	}
	
	
	public void excluirGrupo(){
		System.out.println("Excluir Grupo");
		
		if(gruposAdicionados.contains(grupoSelecionado)){
			gruposAdicionados.remove(grupoSelecionado);
		}
		
		

		
	}
	
	
	private void atualizaBean(){
		this.usuario = new Usuario();
		this.grupo = new Grupo();
		this.gruposAdicionados = new ArrayList<Grupo>();
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
