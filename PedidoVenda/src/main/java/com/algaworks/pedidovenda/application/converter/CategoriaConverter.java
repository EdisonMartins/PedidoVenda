package com.algaworks.pedidovenda.application.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.domain.model.Categoria;
import com.algaworks.pedidovenda.domain.model.repository.CategoriaRepositoryImpl;

@Named
@ViewScoped
@FacesConverter(forClass = Categoria.class, value = "categoriaConverter")
public class CategoriaConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private CategoriaRepositoryImpl categorias;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria retorno = null;

		if (value != null && !value.isEmpty()) {

			return categorias.porId(Long.parseLong(value));

		}

		return retorno;

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		System.out.println("Value: " + value);

		if (value != null && value instanceof Categoria && value != "") {
			Categoria categoria = (Categoria) value;
			return categoria.getId() == null ? null : categoria.getId().toString();

		}

		return "";

	}



}
