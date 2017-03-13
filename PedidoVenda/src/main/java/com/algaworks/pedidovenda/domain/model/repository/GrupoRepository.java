package com.algaworks.pedidovenda.domain.model.repository;

import java.util.List;

import com.algaworks.pedidovenda.domain.model.Grupo;

public interface GrupoRepository extends GenericRepository<Grupo> {
	public Grupo guardar(Grupo grupo);
	public Grupo porNome(String nome);
	public List<Grupo> raizes();
	public void remover(Grupo grupo);
}
