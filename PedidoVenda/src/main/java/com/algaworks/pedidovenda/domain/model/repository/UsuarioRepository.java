package com.algaworks.pedidovenda.domain.model.repository;

import java.util.List;

import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.dto.UsuarioFilter;

public interface UsuarioRepository extends GenericRepository<Usuario> {
	public Usuario guardar(Usuario usuario);
	public Usuario porEmail(String email);
	public void remover(Usuario usuario);
	public List<Usuario> filtrados(UsuarioFilter filtro);
	public int getQuantidadeFiltrados(UsuarioFilter filtro);
	public List<Usuario> getVendedores();
	
}
