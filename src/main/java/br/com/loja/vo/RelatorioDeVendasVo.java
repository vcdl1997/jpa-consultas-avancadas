package br.com.loja.vo;

import java.time.LocalDateTime;

public class RelatorioDeVendasVo {
	
	private String produto;
	private Long quantidadeVendida;
	private LocalDateTime ultimaVenda;
	
	public RelatorioDeVendasVo() {
	}
	
	public RelatorioDeVendasVo(String produto, Long quantidadeVendida, LocalDateTime ultimaVenda) {
		this.produto = produto;
		this.quantidadeVendida = quantidadeVendida;
		this.ultimaVenda = ultimaVenda;
	}

	public String getProduto() {
		return produto;
	}

	public Long getQuantidadeVendida() {
		return quantidadeVendida;
	}

	public LocalDateTime getUltimaVenda() {
		return ultimaVenda;
	}

	@Override
	public String toString() {
		return "RelatorioDeVendasVo [produto=" + produto + ", quantidadeVendida=" + quantidadeVendida + ", ultimaVenda="
				+ ultimaVenda + "]";
	}
}
