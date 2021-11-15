package br.com.loja.testes;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.loja.dao.CategoriaDao;
import br.com.loja.dao.ProdutoDao;
import br.com.loja.modelo.Categoria;
import br.com.loja.modelo.Produto;
import br.com.loja.utils.JpaUtil;

public class Seeder {

	public static void main(String[] args) {
		semearBanco();
		
		Long id = 1l;
		EntityManager em = JpaUtil.getEntityManager();
		ProdutoDao prodDao = new ProdutoDao(em);
		Produto prod = prodDao.buscarPorId(id);
		System.out.println(prod.toString());
		
		List<Produto> produtos = prodDao.listarTodos();
		produtos.forEach(x -> System.out.println(x.toString()));
		Produto find = prodDao.buscarPorCategoria("Livros");
		System.out.println("Encontrado: " + find.toString());
	}
	
	private static void semearBanco() {
		EntityManager em = JpaUtil.getEntityManager();
		CategoriaDao catDao = new CategoriaDao(em);
		ProdutoDao prodDao = new ProdutoDao(em);
		
		Categoria cat1 = new Categoria(null, "Celulares");
		Categoria cat2 = new Categoria(null, "Informática");
		Categoria cat3 = new Categoria(null, "Livros");
		List<Categoria> categorias = Arrays.asList(cat1, cat2, cat3);
		
		Produto prod1 = new Produto(null, "Smartphone", "Smartphone Samsung J4", 899.90, cat1);
		Produto prod2 = new Produto(null, "Livro", "O Codificador Limpo", 39.90, cat3);
		List<Produto> produtos = Arrays.asList(prod1, prod2);
		
		em.getTransaction().begin();
		
		catDao.cadastrarTodos(categorias);
		prodDao.cadastrarTodos(produtos);
		prod1.setDescricao("Smartphone Samsung J4+");
		prodDao.atualizar(prod1);
		prod1.setDescricao("Smartphone Samsung J4+ 64GB");
		prodDao.atualizar(prod1);
		
		em.getTransaction().commit();
		em.close();
	}

}
