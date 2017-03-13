package com.algaworks.pedidovenda.domain.model.repository.dto;

public class UsuarioFilter extends FiltroDataTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private String email;
	
	
	public UsuarioFilter(){
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getPropriedadeOrdenacao() {
		return "nome";
	}
	
	
	

	
}
