package br.com.loja.dao;

import javax.persistence.EntityManager;

import br.com.loja.modelo.Cliente;

public class ClienteDao extends Dao<Cliente>{
	
	public ClienteDao(EntityManager em) {
		super(em);
	}
}
