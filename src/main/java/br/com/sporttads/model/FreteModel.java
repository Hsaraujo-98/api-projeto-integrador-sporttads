package br.com.sporttads.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_frete")
public class FreteModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String descricao;

	private double taxa;

	@Transient
	private double valorFrete = 0;

	public FreteModel() {
	}

	public FreteModel(String descricao, double taxa) {
		this.descricao = descricao;
		this.taxa = taxa;
	}
}
