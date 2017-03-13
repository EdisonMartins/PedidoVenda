package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.Produto;
import com.algaworks.pedidovenda.domain.model.repository.ProdutoRepository;
import com.algaworks.pedidovenda.domain.model.repository.dto.ProdutoFilter;
import com.algaworks.pedidovenda.domain.service.CadastroProdutoService;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Entidade
	private Produto produtoSelecionado;

	// Filtro
	private ProdutoFilter filtro;

	// Lista
	private List<Produto> produtosFiltrados;

	// Repositório
	@Inject
	private ProdutoRepository produtos;
	
	@Inject
	private CadastroProdutoService cadastroProdutoService;

	public PesquisaProdutosBean() {
		filtro = new ProdutoFilter();
	}

	// Getters and setters
	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public Produto getProdutoSelecionado() {
		System.out.println("getProdutoSelecionado()");
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		System.out.println("setProdutoSelecionado()");
		this.produtoSelecionado = produtoSelecionado;
	}

	// Métodos
	public void pesquisar() {
		System.out.println("pesquisar()");
		produtosFiltrados = produtos.filtrados(this.filtro);
	}

	public void excluir (){
		System.out.println("excluir()");
		
		System.out.println("Produto Selecionado: " + this.produtoSelecionado.getSku());
		
		cadastroProdutoService.remover(produtoSelecionado);
		produtosFiltrados.remove(produtoSelecionado);

		FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getNome() + " excluído com sucesso.");
	}

}
