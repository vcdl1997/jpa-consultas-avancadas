package br.com.loja.testes;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.loja.dao.CategoriaDao;
import br.com.loja.dao.ClienteDao;
import br.com.loja.dao.PedidoDao;
import br.com.loja.dao.ProdutoDao;
import br.com.loja.modelo.Categoria;
import br.com.loja.modelo.Cliente;
import br.com.loja.modelo.ItemPedido;
import br.com.loja.modelo.Pedido;
import br.com.loja.modelo.Produto;
import br.com.loja.utils.JpaUtil;

public class Seeder {
	
	private static EntityManager em = JpaUtil.getEntityManager();
	
	private static CategoriaDao categoriaDao = new CategoriaDao(em);
	private static ProdutoDao produtoDao = new ProdutoDao(em);
	private static ClienteDao clienteDao = new ClienteDao(em);
	private static PedidoDao pedidoDao = new PedidoDao(em);

	public static void main(String[] args) {
		semearBanco();
		
		String jpql = "SELECT p from Pedido p WHERE p.id = :id";
		
		em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		
		produtoDao = new ProdutoDao(em);
		pedidoDao = new PedidoDao(em);
		
		Pedido pedido = em.createQuery(jpql, Pedido.class)
			.setParameter("id", 1l)
			.getSingleResult();
		
		System.out.println("Encontrado: " + pedido.toString());
		System.out.println("Valor total vendido: " + pedidoDao.valorTotalVendido());
		
		pedidoDao.relatorioDeVendas().forEach(x -> System.out.println(x.toString()));
		
		System.out.println(pedidoDao.buscarPedidoComClientePorId(1l).toString());
		
		List<Produto> produtos = produtoDao.buscarPorParametros("Smart", null, null);
		produtos.forEach(x -> System.out.println(x.toString()));
		em.close();
	}
	
	private static void semearBanco() {
		Categoria cat1 = new Categoria(null, "Celulares");
		Categoria cat2 = new Categoria(null, "Eletrônicos");
		Categoria cat3 = new Categoria(null, "Livros");
		List<Categoria> categorias = Arrays.asList(cat1, cat2, cat3);
		
		Produto prod1 = new Produto(null, "Smartphone", "Smartphone Samsung J4+ 64GB", 899.90, cat1);
		Produto prod2 = new Produto(null, "Livro", "O Codificador Limpo", 39.90, cat3);
		Produto prod3 = new Produto(null, "SmartTV", "SmartTV Philips 50 polegadas", 2799.90, cat2);
		List<Produto> produtos = Arrays.asList(prod1, prod2, prod3);
		
		Cliente cliente = new Cliente(null, "Bob Green", "654.571.020-68");
		Pedido ped1 = new Pedido(null, cliente);
		Pedido ped2 = new Pedido(null, cliente);
		ItemPedido item1 = new ItemPedido(ped1, prod1, 3);
		ItemPedido item2 = new ItemPedido(ped1, prod2, 1);
		
		ItemPedido item3 = new ItemPedido(ped2, prod3, 10);
		Arrays.asList(item1, item2).forEach(x -> ped1.adicionarItem(x));
		ped2.adicionarItem(item3);
		
		em.getTransaction().begin();
		categoriaDao.cadastrarTodos(categorias);
		produtoDao.cadastrarTodos(produtos);
		clienteDao.cadastrar(cliente);
		pedidoDao.cadastrarTodos(Arrays.asList(ped1, ped2));
		em.getTransaction().commit();
		em.close();
	}
}
