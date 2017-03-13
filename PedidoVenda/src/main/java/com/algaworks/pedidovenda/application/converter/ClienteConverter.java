package com.algaworks.pedidovenda.application.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.domain.model.Cliente;
import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.ClienteRepositoryImpl;

@Named
@ViewScoped
@FacesConverter(forClass = Usuario.class, value = "clienteConverter")
public class ClienteConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteRepositoryImpl clientes;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String clienteString) {
		Cliente retorno = null;

		if (clienteString != null && !clienteString.isEmpty()) {
			return clientes.porId(Long.parseLong(clienteString));

		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && value instanceof Cliente && value != "") {
			Cliente cliente = (Cliente) value;
			return cliente.getId() == null ? null : cliente.getId().toString();

		}

		return "";
	}

}
