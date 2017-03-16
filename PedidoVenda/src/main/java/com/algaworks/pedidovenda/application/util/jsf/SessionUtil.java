package com.algaworks.pedidovenda.application.util.jsf;

import javax.faces.context.FacesContext;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.security.UsuarioSistema;

public class SessionUtil {
	public static Usuario getUsuario() {
		UsuarioSistema usuario = null;
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
		}

		if (usuario != null) {
			return usuario.getUsuario();

		} else {
			return null;
		}

	}
}
