package br.com.loja.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
			.setMaxResults(1)
			.getSingleResult();
	}
	
	public List<Produto> listarTodos() {
		String jpql = "SELECT p FROM Produto p";
		return this.getEm().createQuery(jpql, Produto.class).getResultList();
	}
	
	public List<Produto> buscarPorParametros(
		String nome,
		Double preco,
		LocalDate dataCadastro
	){
		String jpql = "SELECT p FROM Produto p WHERE 1=1 ";
		
		if(nome != null && !nome.trim().isEmpty()) jpql += " AND p.nome like CONCAT('%', :nome,'%')";
		if(preco != null) jpql += " AND p.preco = :preco";
		if(dataCadastro != null) jpql += " AND p.dataCadastro = :dataCadastro";
		
		TypedQuery<Produto> query = this.getEm().createQuery(jpql, Produto.class);
		
		if(nome != null && !nome.trim().isEmpty()) query.setParameter("nome", nome);
		if(preco != null) query.setParameter("preco", preco);
		if(dataCadastro != null) query.setParameter("dataCadastro", dataCadastro);
		
		return query.getResultList();
	}
}
