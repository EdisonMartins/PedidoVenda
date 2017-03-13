package com.algaworks.pedidovenda.application.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.UsuarioRepositoryImpl;

@Named
@ViewScoped
@FacesConverter(forClass = Usuario.class, value = "usuarioConverter")
public class UsuarioConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioRepositoryImpl usuarios;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String usuarioString) {
		System.out.println("UsuarioConverter: usuarioString: " + usuarioString);
		
		Usuario retorno = null;
		

		if (usuarioString != null && !usuarioString.isEmpty()) {
			return usuarios.porId(Long.parseLong(usuarioString));

		}
		
		
		return retorno; 
		


	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null && value instanceof Usuario && value != "") {
			Usuario usuario = (Usuario) value;
			return usuario.getId() == null ? null : usuario.getId().toString();

		}

		return "";

	}

}
