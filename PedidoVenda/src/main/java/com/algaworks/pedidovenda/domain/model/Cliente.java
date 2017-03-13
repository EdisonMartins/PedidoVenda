package com.algaworks.pedidovenda.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String email;
	private String documentoReceitaFederal;
	private TipoPessoa tipo;

	// Relecionamentos
	private List<Endereco> enderecos = new ArrayList<>();

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
	@Size(max = 80)
	@Column(nullable = false, length = 80)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Email
	@NotBlank
	@Size(max = 60)
	@Column(unique = true, nullable = false, length = 60)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	public TipoPessoa getTipo() {
		return tipo;
	}

	public void setTipo(TipoPessoa tipo) {
		this.tipo = tipo;
	}

	@NotBlank
	@Size(max = 18)
	@Column(name = "doc_receita_federal", nullable = false, length = 18)
	public String getDocumentoReceitaFederal() {
		return documentoReceitaFederal;
	}

	public void setDocumentoReceitaFederal(String documentoReceitaFederal) {
		this.documentoReceitaFederal = documentoReceitaFederal;
	}

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Transient
	public boolean isNaoExistente() {
		return !isExistente();
	}

	@Transient
	public boolean isExistente() {
		return this.getId() != null;
	}

	@Override
	public String toString() {
		return "Id: " + this.getId() + " " + this.getNome();
	}

	public void adicionarEnderecoVazio() {
		this.enderecos.add(new Endereco());
	}

	public void addEndereco(Endereco endereco) {
		this.enderecos.add(endereco);
	}

	public void removeEndereco(Endereco endereco) {
		this.enderecos.remove(endereco);
	}

}
