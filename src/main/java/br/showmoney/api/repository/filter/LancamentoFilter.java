package br.showmoney.api.repository.filter;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoFilter {
	
	private String descricao;
	private LocalDate dataVencimentoDe;
	private LocalDate dataVencimentoAte;

}
