package com.algaworks.pedidovenda.application.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.repository.PedidoRepositoryImpl;

@Named
@ViewScoped
@FacesConverter(forClass = Pedido.class, value = "pedidoConverter")
public class PedidoConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private PedidoRepositoryImpl pedidoRep;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String pedidoString) {
		System.out.println("PedidoConverter: produtoString: " + pedidoString);
		
		Pedido retorno = null;
		

		if (pedidoString != null && !pedidoString.isEmpty()) {
			return pedidoRep.porId(Long.parseLong(pedidoString));

		}
		
		
		return retorno; 
		


	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null && value instanceof Pedido && value != "") {
			Pedido pedido = (Pedido) value;
			return pedido.getId() == null ? null : pedido.getId().toString();

		}

		return "";

	}

}
