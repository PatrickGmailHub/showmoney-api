package br.showmoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.showmoney.api.model.Lancamento;
import br.showmoney.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
