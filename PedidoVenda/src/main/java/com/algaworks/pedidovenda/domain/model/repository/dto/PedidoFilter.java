package com.algaworks.pedidovenda.domain.model.repository.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.algaworks.pedidovenda.domain.model.StatusPedido;


public class PedidoFilter extends FiltroDataTable {
	
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long numeroDe;
	private Long numeroAte;
	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String nomeVendedor;
	private String nomeCliente;
	private StatusPedido[] statuses;
	private String propriedadeOrdenacao;
	
	

	public void setPropriedadeOrdenacao(String propriedadeOrdenacao) {
		this.propriedadeOrdenacao = propriedadeOrdenacao;
	}

	@Override
	public String getPropriedadeOrdenacao() {
		return this.propriedadeOrdenacao;
	}

	public Long getNumeroDe() {
		return numeroDe;
	}

	public void setNumeroDe(Long numeroDe) {
		this.numeroDe = numeroDe;
	}

	public Long getNumeroAte() {
		return numeroAte;
	}

	public void setNumeroAte(Long numeroAte) {
		this.numeroAte = numeroAte;
	}

	public Date getDataCriacaoDe() {
		return dataCriacaoDe;
	}

	public void setDataCriacaoDe(Date dataCriacaoDe) {
		this.dataCriacaoDe = dataCriacaoDe;
	}

	public Date getDataCriacaoAte() {
		return dataCriacaoAte;
	}

	public void setDataCriacaoAte(Date dataCriacaoAte) {
		this.dataCriacaoAte = dataCriacaoAte;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public StatusPedido[] getStatuses() {
		return statuses;
	}

	public void setStatuses(StatusPedido[] statuses) {
		this.statuses = statuses;
	}
	
	
	@Override
	public String toString() {
		SimpleDateFormat SDF = new SimpleDateFormat(
				"dd/MM/yyyy");
		
		StringBuilder valor = new StringBuilder();
		valor.append("Numero de " + this.numeroDe + " até " + this.numeroAte  + "\n");
		
		if(this.dataCriacaoDe != null){
			valor.append("Date de Criação de " + SDF.format(dataCriacaoDe) + "\n");
		}
		
		if(this.dataCriacaoDe != null){
			valor.append("Date de Criação até " + SDF.format(dataCriacaoAte)  + "\n");
		}
		
		
		valor.append("Vendedor: " + this.nomeVendedor + "\n");
		valor.append("Cliente: " + this.nomeCliente + "\n");
		valor.append("Status: " + this.statuses);
		
		return valor.toString();
		
		
	}

	
	
}















