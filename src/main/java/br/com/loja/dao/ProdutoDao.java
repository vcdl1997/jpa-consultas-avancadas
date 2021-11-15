package br.com.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.loja.modelo.Produto;

public class ProdutoDao extends Dao<Produto>{
	
	public ProdutoDao(EntityManager em) {
		super(em);
	}
	
	public Produto buscarPorId(Long id) {
		return this.getEm().find(Produto.class, id);
	}
	
	public Produto buscarPorNome(String nome) {
		
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
		
		return this.getEm()
			.createQuery(jpql, Produto.class)
			.setParameter("nome", nome)
			.getSingleResult();
	}
	
	public Produto buscarPorCategoria(String nome) {
		
		String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
		
		return this.getEm()
			.createQuery(jpql, Produto.class)
			.setParameter("nome", nome)
			.getSingleResult();
	}
	
	public List<Produto> listarTodos() {
		String jpql = "SELECT p FROM Produto p";
		return this.getEm().createQuery(jpql, Produto.class).getResultList();
	}
}
