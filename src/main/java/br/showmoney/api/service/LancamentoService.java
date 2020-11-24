package br.showmoney.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.showmoney.api.model.Lancamento;
import br.showmoney.api.model.Pessoa;
import br.showmoney.api.repository.LancamentoRepository;
import br.showmoney.api.repository.PessoaRepository;
import br.showmoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		Optional<Pessoa> optionalPessoa = pessoaRepository.findById(lancamento.getPessoa().getId());
		
		if(!optionalPessoa.isPresent() || !optionalPessoa.get().getAtivo()) {
			throw new PessoaInexistenteOuInativaException("Pessoa não pode ser inativa ou inexistente");
		}
		
		return lancamentoRepository.save(lancamento);
	}

}
