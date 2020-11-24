package br.showmoney.api.repository.lancamento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.showmoney.api.model.Lancamento;
import br.showmoney.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public List<Lancamento> filtrar(LancamentoFilter filter);
	public Page<Lancamento> filtrarPaginado(LancamentoFilter filter, Pageable pageable);

}
