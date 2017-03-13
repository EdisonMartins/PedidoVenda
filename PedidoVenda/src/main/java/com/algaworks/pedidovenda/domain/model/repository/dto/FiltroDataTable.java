package com.algaworks.pedidovenda.domain.model.repository.dto;

import java.io.Serializable;


public abstract class FiltroDataTable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int primeiroRegistro;
	protected int quantidadeRegistro;
	protected String direcaoOrdenacao;
	protected int colunaOrdenacao;
	protected boolean ascendente;

		
	

	// Getters and Setters
	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}

	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}

	public int getQuantidadeRegistro() {
		return quantidadeRegistro;
	}

	public void setQuantidadeRegistro(int quantidadeRegistro) {
		this.quantidadeRegistro = quantidadeRegistro;
	}

	public String getDirecaoOrdenacao() {
		return direcaoOrdenacao;
	}

	public void setDirecaoOrdenacao(String propriedadeOrdenacao) {
		this.direcaoOrdenacao = propriedadeOrdenacao;
	}
	
	public int getColunaOrdenacao() {
		return colunaOrdenacao;
	}

	public void setColunaOrdenacao(int colunaOrdenacao) {
		this.colunaOrdenacao = colunaOrdenacao;
	}



	public boolean isAscendente() {
		return ascendente;
	}

	

	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}
	
	public abstract String getPropriedadeOrdenacao();

	
	
	


}
