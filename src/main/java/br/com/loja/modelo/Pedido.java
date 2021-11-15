package br.com.loja.modelo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime data = LocalDateTime.now();
	
	/* Todo relacionamento terminado em ToOne, por default o parametro fetch é EAGER, 
	 * com isso o conteudo da entidade relacionada é carregado junto.
	 * 
	 * Para ajustar esse comportamento, atribuimos ao parametro fetch o valor "LAZY", 
	 * desta forma só haverá o "join" com a entidade se algum valor for acessado.
	 * 
	 * EAGER -> é carregado automaticamente
	 * LAZY -> é carregado de forma tardia (apenas quando acessamos algum get da entidade relacionada)
	 * */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Column(name = "valor_total")
	private Double valorTotal;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Pedido() {
	}

	public Pedido(Long id, Cliente cliente) {
		this.id = id;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getData() {
		return data;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}
	
	public void adicionarItem(ItemPedido itemPedido) {
		this.itens.add(itemPedido);
		Double total = this.getValorTotal();
		this.setValorTotal(total);
	}
	
	public void removerItem(ItemPedido itemPedido) {
		this.itens.removeIf(x -> x.hashCode() == itemPedido.hashCode());
		Double total = this.getValorTotal();
		this.setValorTotal(total);
	}

	public Double getValorTotal() {
		Double total = 0.0;
		
		for(ItemPedido item : this.itens) {
			total += item.subtotal();
		}
		
		return total;
	}
	
	private void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", data=" + data + ", cliente=" + cliente + ", valorTotal=" + valorTotal
				+ ", itens=" + itens + "]";
	}
}
