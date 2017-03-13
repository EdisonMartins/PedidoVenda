package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.Categoria;
import com.algaworks.pedidovenda.domain.model.Produto;
import com.algaworks.pedidovenda.domain.model.repository.CategoriaRepository;
import com.algaworks.pedidovenda.domain.service.CadastroProdutoService;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaRepository categorias;

	private Produto produto;
	private Categoria categoriaPai;

	// Listas
	private List<Categoria> categoriasRaizes;
	private List<Categoria> subcategorias;

	// Services
	@Inject
	private CadastroProdutoService cadastroProdutoService;

	public CadastroProdutoBean() {
		System.out.println("CadastroProdutoBean()");
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializar()");

		if (FacesUtil.isNotPostBack()) {
			this.categoriasRaizes = categorias.raizes();
		}

		if (this.categoriaPai != null) {
			carregarSubcategorias();
		}

	}

	public void setProduto(Produto produto) {
		System.out.println("SetProduto()");
		this.produto = produto;

		if (this.produto != null) {
			this.categoriaPai = this.produto.getCategoria().getCategoriaPai();

		}

	}

	public Produto getProduto() {
		return produto;
	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}

	@NotNull
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	// Métodos
	public void salvar() {
		this.produto = cadastroProdutoService.salvar(produto);
		limpar();

		FacesUtil.addInfoMessage("Produto salvo com sucesso!");

	}

	private void limpar() {
		this.produto = new Produto();
		categoriaPai = null;
		subcategorias = new ArrayList<Categoria>();
	}

	public void carregarSubcategorias() {
		subcategorias = categorias.subcategoriasDe(this.categoriaPai);
	}

	public boolean isEditando() {
		return this.produto.getId() != null;
	}

}
