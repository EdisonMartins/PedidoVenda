package com.algaworks.pedidovenda.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

import com.algaworks.pedidovenda.domain.model.validation.SKU;
import com.algaworks.pedidovenda.domain.service.exception.NegocioExceptionImpl;

@Entity
@Table(name = "produto")
@XmlRootElement
@NamedQueries(value = { @NamedQuery(name = "Produto.porSku", query = "from Produto where sku = :sku") })
public class Produto implements Serializable {

	public static final String POR_SKU = "Produto.porSku";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String nome;

	private String sku;

	private BigDecimal valorUnitario;

	private Integer quantidadeEstoque;

	// Relacionamento

	private Categoria categoria;

	// Getters and Setters
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotBlank
	@SKU
	@Column(unique = true, nullable = false, length = 20)
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotNull
	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@NotNull
	@Min(0)
	@Max(value = 9999, message = "Tem um valor superior a 9999")
	@Column(name = "quantidade_estoque", nullable = false, length = 5)
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		System.out.println("Categoria inserida: " + categoria.getDescricao());
		this.categoria = categoria;
	}

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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void baixarQuantidade(Integer quantidade) {
		int novaQuantidade = this.getQuantidadeEstoque() - quantidade;

		if (novaQuantidade < 0) {
			throw new NegocioExceptionImpl("Não há disponibilidade no estoque de " + quantidade + " item do produto "
					+ this.getSku() + ".");
		}

		this.setQuantidadeEstoque(novaQuantidade);

	}

	public void adicionarEstoque(Integer quantidade) {
		this.setQuantidadeEstoque(getQuantidadeEstoque() + quantidade);
	}
	
	@Override
	public String toString() {
		StringBuilder valor = new StringBuilder();
		valor.append("Id: " + this.id + "\n");
		valor.append("Nome: " + this.nome + "\n");
		valor.append("Quantidade em estoque: " + this.quantidadeEstoque + "\n");
		valor.append("Categoria Id: " + this.categoria.getId() + "\n");
		valor.append("Categoria: " + this.categoria.getDescricao() + "\n");
		
		
		
		
		return valor.toString();
	}
	
	

}
