package br.showmoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.showmoney.api.model.Lancamento;
import br.showmoney.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryQueryImpl implements LancamentoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Lancamento> filtrar(LancamentoFilter lancamentofilter) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		//Criar as restrições
		Predicate[] predicates = criarRestricoes(lancamentofilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	@Override
	public Page<Lancamento> filtrarPaginado(LancamentoFilter filter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		//Criar as restrições
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		
		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentofilter, CriteriaBuilder builder, Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(StringUtils.hasText((lancamentofilter.getDescricao()))) {
			predicates.add(builder.like(builder.lower(root.get("descricao")), "%" + lancamentofilter.getDescricao().toLowerCase() + "%"));
		}
		
		if(lancamentofilter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"), lancamentofilter.getDataVencimentoDe()));
		}

		if(lancamentofilter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataVencimento"), lancamentofilter.getDataVencimentoAte()));
		}
		
		//return predicates.toArray(new Predicate[predicates.size()]);
		
		Predicate[] predicateArray = new Predicate[predicates.size()];
		
		return predicates.toArray(predicateArray);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDapaginaCorrente = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDapaginaCorrente);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(LancamentoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}
	
}
