package com.algaworks.pedidovenda.application.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.pedidovenda.domain.model.Produto;
import com.algaworks.pedidovenda.domain.model.repository.ProdutoRepositoryImpl;

@Named
@ViewScoped
@FacesConverter(forClass = Produto.class, value = "produtoConverter")
public class ProdutoConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private ProdutoRepositoryImpl produtos;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String produtoString) {	
		Produto retorno = null;
		if (StringUtils.isNotEmpty(produtoString)) {
			return produtos.porId(Long.parseLong(produtoString));

		}
		return retorno; 
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && value instanceof Produto && value != "") {
			Produto produto = (Produto) value;
			return produto.getId() == null ? null : produto.getId().toString();

		}
		return "";

	}
}
