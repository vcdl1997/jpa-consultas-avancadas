package br.com.loja.dao;

import javax.persistence.EntityManager;

import br.com.loja.modelo.Categoria;

public class CategoriaDao extends Dao<Categoria>{
	public CategoriaDao(EntityManager em) {
		super(em);
	}
}
