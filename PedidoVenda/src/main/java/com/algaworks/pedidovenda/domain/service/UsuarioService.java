package com.algaworks.pedidovenda.domain.service;

import com.algaworks.pedidovenda.domain.model.Grupo;
import com.algaworks.pedidovenda.domain.model.Usuario;

public interface UsuarioService extends GenericService<Usuario> {
	public void adicionarGrupoEmUsuario(Grupo grupo, Usuario usuario);
	public void excluirGrupoEmUsuario(Grupo grupo, Usuario usuario);
	public void excluir(Usuario usuarioSelecionado);

}
