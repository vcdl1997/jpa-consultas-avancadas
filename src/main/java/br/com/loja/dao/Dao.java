package br.com.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;

public class Dao<G> {
	
	private EntityManager em;

	public Dao(EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}

	public void cadastrar(G g) {
		this.em.persist(g);
	}
	
	public void cadastrarTodos(List<G> list) {
		for(G g : list) {
			this.cadastrar(g);
		}
	}
	
	public void atualizar(G g) {
//		Garante que a entidade está sendo persistida pela JPA 
		this.em.merge(g);
		this.em.flush();
	}
	
	public void atualizarTodos(List<G> list) {
		for(G g : list) {
			this.atualizar(g);
		}
	}
	
	public void remover(G g) {
//		Garante que a entidade está sendo persistida pela JPA 
		this.em.merge(g);
		this.em.remove(g);
	}
	
	public void removerTodos(List<G> list) {
		for(G g : list) {
			this.remover(g);
		}
	}
}
