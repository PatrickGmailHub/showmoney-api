package br.showmoney.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.showmoney.api.model.enums.TipoLancamento;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Lancamento {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;
	
	@Column(name = "data_vencimento")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataVencimento;
	
	@Column(name = "data_pagamento")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataPagamento;
	
	private BigDecimal valor;
	
	private String observacao;
	
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@ManyToOne
	@JoinColumn(name = "codigo_categoria")
	@NotNull(message = "O campo categoria é obrigatório")
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name = "id_pessoa")
	@NotNull(message = "O campo pessoa é obrigatório")
	private Pessoa pessoa;
	
}
