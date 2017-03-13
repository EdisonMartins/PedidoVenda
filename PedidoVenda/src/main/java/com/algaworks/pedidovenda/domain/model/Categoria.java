package com.algaworks.pedidovenda.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
@NamedQueries(value = {
		@NamedQuery(name = "Categoria.subcategorias", query = "SELECT c from Categoria c where categoriaPai = :raiz"),
		@NamedQuery(name = "Categoria.categoriasSemPai", query = "SELECT c from Categoria c where categoriaPai is null") })
public class Categoria implements Serializable {

	public static final String SUBCATEGORIAS = "Categoria.subcategorias";
	public static final String CATEGORIAS_SEM_PAI = "Categoria.categoriasSemPai";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String descricao;

	// Auto-Relacionamento
	private List<Categoria> subcategorias = new ArrayList<>();
	private Categoria categoriaPai;

	// Getters and Setters
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false, length = 255)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@OneToMany(mappedBy = "categoriaPai", cascade = CascadeType.ALL)
	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<Categoria> subCategorias) {
		this.subcategorias = subCategorias;
	}

	@ManyToOne
	@JoinColumn(name = "categoria_pai_id")
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
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
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getDescricao();
	}

}
