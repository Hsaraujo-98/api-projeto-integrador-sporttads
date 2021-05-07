package br.com.sporttads.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_carrinho")
@Data
@NoArgsConstructor
public class CarrinhoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carrinho")
	private int id;

	@OneToMany(mappedBy = "carrinho")
	private List<ItemCarrinhoModel> itens;

	@OneToOne
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;

	private double total = 0;

	private int quantidadeTotal = 0;

	private double valorFrete = 0;

	@OneToOne
	@JoinColumn(name = "id_frete")
	private FreteModel frete;

	public void calcularTotal() {
		this.total = 0;
		for (ItemCarrinhoModel itemCarrinho : this.itens) {
			this.total += itemCarrinho.getSubtotal();
		}
		quantidadeTotal = this.itens.size();
		// this.calcularFrete();
		// this.total += this.frete;
	}

	public void setItens(List<ItemCarrinhoModel> itens) {
		this.itens = itens;
	}

//	public void calcularFrete() {
//		Random random = new Random();
//		double taxa = 0;
//		if (this.total > 0 && this.total <= 100) {
//			taxa = (random.nextDouble()) / 10;
//			this.frete = this.total * taxa;
//			return;
//		} else if (this.total > 100 && this.total <= 999) {
//			taxa = (random.nextDouble()) / 100;
//			this.frete = this.total * taxa;
//			return;
//		} else {
//			this.frete = 0;
//			return;
//		}
//	}
}
