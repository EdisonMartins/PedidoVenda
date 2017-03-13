package com.algaworks.pedidovenda.domain.model.repository;

import java.io.Serializable;
import java.util.List;

import com.algaworks.pedidovenda.domain.model.Categoria;

public interface CategoriaRepository extends Serializable, GenericRepository<Categoria> {
	public List<Categoria> subcategoriasDe(Categoria categoriaPai);
	public List<Categoria> raizes();
}
