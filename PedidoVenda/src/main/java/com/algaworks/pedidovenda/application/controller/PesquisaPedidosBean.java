package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.application.controller.component.LazyPedidoDataModel;
import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.StatusPedido;
import com.algaworks.pedidovenda.domain.model.repository.Pedidos;
import com.algaworks.pedidovenda.domain.model.repository.dto.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3764745591012623625L;
	private PedidoFilter filtro;
	private LazyPedidoDataModel model;
	
	@Inject
	private Pedidos pedidoRep;

	public PesquisaPedidosBean() {
		filtro = new PedidoFilter();
	}

	public void inicializar(){
		
		if(FacesUtil.isNotPostBack()){
			model = new LazyPedidoDataModel(pedidoRep, filtro);
		}
		
	}

	public LazyPedidoDataModel getModel() {
		return model;
	}

	public PedidoFilter getFiltro() {
		return filtro;
	}
	
	public StatusPedido[] getStatuses(){
		return StatusPedido.values();
	}
	
	

}
