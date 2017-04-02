package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import com.algaworks.pedidovenda.application.controller.annotation.PedidoEdicao;
import com.algaworks.pedidovenda.application.controller.event.PedidoAlteradoEvent;
import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.Cliente;
import com.algaworks.pedidovenda.domain.model.EnderecoEntrega;
import com.algaworks.pedidovenda.domain.model.ItemPedido;
import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.Produto;
import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.enums.FormaPagamento;
import com.algaworks.pedidovenda.domain.model.repository.Clientes;
import com.algaworks.pedidovenda.domain.model.repository.ProdutoRepository;
import com.algaworks.pedidovenda.domain.model.repository.Usuarios;
import com.algaworks.pedidovenda.domain.model.validation.SKU;
import com.algaworks.pedidovenda.domain.service.CadastroPedidoService;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Permite que outro bean seja injetado com este pedido. Compartilha essa
	 * variável com outro bean Ver {@link EmissaoPedidoBean}
	 */
	@Produces
	@PedidoEdicao
	private Pedido pedido;
	private List<Usuario> vendedores;

	@Inject
	private Usuarios usuarioRep;

	@Inject
	private Clientes clienteRep;

	@Inject
	private CadastroPedidoService cadastroPedidoService;

	@Inject
	private ProdutoRepository produtoRep;

	private Produto produtoLinhaEditavel;

	@SKU
	private String sku;

	public CadastroPedidoBean() {
		System.out.println("Contrutor: CadastroPedidoBean()");
		limpar();

	}

	public void inicializar() {
		System.out.println("isNotPostBack(");
		vendedores = usuarioRep.getVendedores();
		this.pedido.adicionarItemVazio();

		// Chama este método aqui, pois aqui o entityManager ainda está
		// aberto.
		this.recalcularPedido();

		System.out.println("Items Dps" + pedido.getItens());
	}

	// Getters and Setters
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Usuario> getVendedores() {

		return vendedores;
	}

	// Métodos
	public void clienteSelecionado(SelectEvent event) {
		pedido.setCliente((Cliente) event.getObject());
	}

	private void limpar() {
		System.out.println("limpar()");
		pedido = new Pedido();

		pedido.setEnderecoEntrega(new EnderecoEntrega());
	}

	public void pedidoAlterado(@Observes PedidoAlteradoEvent event) {
		this.pedido = event.getPedido();
	}

	public void salvar() {
		System.out.println("CadastroPedidoBean: salvar()");

		try {
			this.pedido.removerItemVazio();

			this.pedido = cadastroPedidoService.salvar(pedido);
			FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
		} finally {
			// Catch já é tratado pelo JSFExceptionHandler
			this.pedido.adicionarItemVazio();
		}

	}

	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}

	public boolean isEditando() {
		return this.pedido.getId() != null;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public List<Produto> completarProduto(String nome) {
		System.out.println("completarProduto()");
		return produtoRep.porNome(nome);

	}

	// Ajax
	public List<Cliente> completarCliente(String nome) {
		System.out.println("completarCliente");
		return clienteRep.porNome(nome);
	}

	public void carregarProdutoLinhaEditavel() {
		System.out.println("carregarProdutoLinhaEditavel()");

		ItemPedido item = this.pedido.getItens().get(0);

		if (this.produtoLinhaEditavel != null) {

			if (existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
			} else {

				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());

				this.pedido.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;

				this.pedido.recalcularValorTotal();

			}
		}

	}

	public void recalcularPedido() {
		System.out.println("recalcularPedido()");

		if (this.pedido != null) {
			this.pedido.recalcularValorTotal();

		}

	}

	public void carregarProdutoPorSKU() {
		System.out.println("carregarProdutoPorSKU()");
		if (StringUtils.isNotEmpty(this.sku)) {
			this.produtoLinhaEditavel = this.produtoRep.porSku(sku);

			if (produtoLinhaEditavel == null) {
				FacesUtil.addErrorMessage("Produto não encontrado com SKU informado.");
			} else {
				carregarProdutoLinhaEditavel();
			}

		}

	}

	public void atualizarQuantidade(ItemPedido item, int linha) {
		System.out.println("atualizarQuantidade(ItemPedido item, int linha)");
		System.out.println("Item: " + item);
		System.out.println("Linha: " + linha);

		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				this.getPedido().getItens().remove(linha);
			}
		}

		this.pedido.recalcularValorTotal();

	}

	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemPedido item : this.getPedido().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}

		return existeItem;
	}

}
