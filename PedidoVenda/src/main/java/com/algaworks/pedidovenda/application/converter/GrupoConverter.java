package com.algaworks.pedidovenda.application.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.domain.model.Grupo;
import com.algaworks.pedidovenda.domain.model.repository.GrupoRepositoryImpl;

@Named
@ViewScoped
@FacesConverter(forClass = Grupo.class, value = "grupoConverter")
public class GrupoConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private GrupoRepositoryImpl grupos;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String grupoString) {
		System.out.println("GrupoConverter: grupoString: " + grupoString);
		
		Grupo retorno = null;
		

		if (grupoString != null && !grupoString.isEmpty()) {
			return grupos.porId(Long.parseLong(grupoString));

		}
		
		
		return retorno; 
		


	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null && value instanceof Grupo && value != "") {
			Grupo grupo = (Grupo) value;
			return grupo.getId() == null ? null : grupo.getId().toString();

		}

		return "";

	}

}
