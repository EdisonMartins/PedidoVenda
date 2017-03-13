package com.algaworks.pedidovenda.domain.model;

public enum TipoPessoa {
	FISICA("Física"), JURIDICA("Jurídica");
	
	private String nome;
	
	TipoPessoa(String nome){
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	
	
}
