package com.algaworks.pedidovenda.domain.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.algaworks.pedidovenda.domain.model.Grupo;
import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.Usuarios;
import com.algaworks.pedidovenda.domain.service.exception.NegocioExceptionImpl;

public class UsuarioServiceImpl implements Serializable, UsuarioService {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;


	@Transactional
	@Override
	public Usuario salvar(Usuario usuario) {
		
		
		
		
		
		Usuario usuarioExistente = usuarios.porEmail(usuario.getEmail());

		// Validação que permite editar um determinado Usuario.
		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioExceptionImpl("Já existe um Usuario com o e-Mail informado.");
		}
		
		System.out.println("Grupos: " + usuario.getGrupos());
		

		return usuarios.guardar(usuario);
	}

	@Override
	public void adicionarGrupoEmUsuario(Grupo grupo, Usuario usuario) {

		if (!usuario.getGrupos().contains(grupo)) {
			usuario.getGrupos().add(grupo);

		} else {
			throw new NegocioExceptionImpl("Grupo " + grupo.getNome() + " já adicionado");
		}

	}

	@Override
	public void excluirGrupoEmUsuario(Grupo grupo, Usuario usuario) {
		if (usuario.getGrupos().contains(grupo)) {
			usuario.getGrupos().remove(grupo);

		} 
		
	}

	@Transactional
	@Override
	public void excluir(Usuario usuarioSelecionado) {
		try {
			usuarios.remover(usuarioSelecionado);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioExceptionImpl("Usuário não pode ser excluído");
		}
		
		
		
		
		
	}

}
