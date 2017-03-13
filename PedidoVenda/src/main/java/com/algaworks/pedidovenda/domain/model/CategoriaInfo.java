package com.algaworks.pedidovenda.domain.model;

public class CategoriaInfo {
	private String categoria;
	private Integer quantidadeDeProdutos;

	public CategoriaInfo(String categoria, Integer quantidadeDeProdutos) {
		super();
		this.categoria = categoria;
		this.quantidadeDeProdutos = quantidadeDeProdutos;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getQuantidadeDeProdutos() {
		return quantidadeDeProdutos;
	}

	public void setQuantidadeDeProdutos(Integer quantidadeDeProdutos) {
		this.quantidadeDeProdutos = quantidadeDeProdutos;
	}
	
	@Override
	public String toString() {
		return this.categoria + " " + this.quantidadeDeProdutos;
	}

}
