package com.algaworks.pedidovenda.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.algaworks.pedidovenda.domain.model.enums.FormaPagamento;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Date dataCriacao;
	private String observacao;
	private Date dataEntrega;
	private BigDecimal valorFrete = BigDecimal.ZERO;
	private BigDecimal valorDesconto = BigDecimal.ZERO;
	private BigDecimal valorTotal = BigDecimal.ZERO;

	// Relacionamentos
	private Cliente cliente;
	private Usuario vendedor;
	private EnderecoEntrega enderecoEntrega;
	private List<ItemPedido> itens = new ArrayList<>();

	// Enum
	private StatusPedido status = StatusPedido.ORCAMENTO;
	private FormaPagamento formaPagameto;

	// Construtor
	public Pedido() {
		System.out.println("Construtor: Pedido()");
	}

	// Getters and Setters

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Column(columnDefinition = "text")
	// Texto Grande
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_entrega", nullable = false)
	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	@NotNull
	@Column(name = "valor_frete", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	@NotNull
	@Column(name = "valor_desconto", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	@NotNull
	@Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "vendedo_id", nullable = false)
	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	@Embedded
	public EnderecoEntrega getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	// orphanRemoval = true; Permite excluir um Item do pedido e o Hibernate irá
	// descartá-lo.
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "forma_pagamento", nullable = false, length = 20)
	public FormaPagamento getFormaPagameto() {
		return formaPagameto;
	}

	public void setFormaPagameto(FormaPagamento formaPagameto) {
		this.formaPagameto = formaPagameto;
	}

	// Métodos
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Transient
	public boolean isNovo() {
		return this.getId() == null;
	}

	@Transient
	public boolean isExistente() {
		return !isNovo();
	}

	@Transient
	public BigDecimal getValorSubTotal() {
		return this.getValorTotal().subtract(getValorFrete()).add(getValorDesconto());
	}

	@Transient
	public boolean isOrcamento() {
		return StatusPedido.ORCAMENTO.equals(this.getStatus());
	}
	
	@Transient
	public boolean isValorTotalNegativo() {
		return this.getValorTotal().compareTo(BigDecimal.ZERO) < 0;
	}

	/**
	 * Este método só pode ser chamado quan23do o EntityManager estiver aberto.
	 * Está não é uma boa abordagem.
	 */
	public void recalcularValorTotal() {
		BigDecimal total = BigDecimal.ZERO;
		total = total.add(valorFrete).subtract(valorDesconto);

		// getItens lançará LazyException
		for (ItemPedido item : getItens()) {
			if (item.getProduto() != null && item.getProduto().getId() != null) {
				total = total.add(item.getValorTotal());
			}

		}

		this.setValorTotal(total);
	}

	public void adicionarItemVazio() {
		System.out.println("adicionarItemVazio()");
		if (this.isOrcamento()) {

			Produto produto = new Produto();

			ItemPedido item = new ItemPedido();
			item.setProduto(produto);
			item.setPedido(this);

			this.getItens().add(0, item);
		}

	}

	public void removerItemVazio() {
		ItemPedido primeiroItem = this.getItens().get(0);

		if (primeiroItem != null && primeiroItem.getProduto().getId() == null) {
			this.getItens().remove(0);
		}
	}

	@Transient
	public boolean isEmitido() {
		return StatusPedido.EMITIDO.equals(this.getStatus());
	}

	@Transient
	public boolean isNaoEmissivel() {
		return !this.isEmissivel();
	}

	@Transient
	public boolean isEmissivel() {
		return this.isExistente() && this.isOrcamento();
	}

	@Transient
	public boolean isCancelavel() {
		return this.isExistente() && !this.isCancelado();
	}
	
	@Transient
	public boolean isCancelado(){
		return StatusPedido.CANCELADO.equals(this.getStatus());
	}
	
	@Transient
	public boolean isNaoCancelavel() {
		return !this.isCancelavel();
	}

	@Transient
	public boolean isNaoAlteravel() {
		return !this.isAlteravel();
	}

	@Transient
	public boolean isAlteravel() {
		return this.isOrcamento();
	}
	
	@Transient
	public boolean isNaoEnviavelPorEmail(){
		return this.isNovo() || this.isCancelado();
	}
	
	

	
	

}

























































