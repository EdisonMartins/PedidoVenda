package com.algaworks.pedidovenda.domain.model.repository.dto;

public class ClienteFilter extends FiltroDataTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private String documentoReceitaFederal;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumentoReceitaFederal() {
		return documentoReceitaFederal;
	}

	public void setDocumentoReceitaFederal(String numeroReceitaFederal) {
		this.documentoReceitaFederal = numeroReceitaFederal;
	}

	@Override
	public String getPropriedadeOrdenacao() {
		return "nome";
	}

	@Override
	public String toString() {
		StringBuilder valor = new StringBuilder();
		valor.append("Nome: " + this.nome + "\n");
		valor.append("Numero Receita Federal: " + this.documentoReceitaFederal + "\n");

		return valor.toString();
	}

}
