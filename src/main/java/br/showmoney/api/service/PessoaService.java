package br.showmoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.showmoney.api.model.Endereco;
import br.showmoney.api.model.Pessoa;
import br.showmoney.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa atualizar(Integer id, Pessoa pessoa) {
		Optional<Pessoa> pessoaSalva = buscarPessoaPeloCodigo(id);
  	BeanUtils.copyProperties(pessoa, pessoaSalva.get(), "id");
  	
  	return pessoaRepository.save(pessoaSalva.get());
	}

	public void atualizarPropriedadeAtivo(Integer id, Boolean ativo) {
		Optional<Pessoa> pessoaSalva = buscarPessoaPeloCodigo(id);
		pessoaSalva.get().setAtivo(ativo);
		
		pessoaRepository.save(pessoaSalva.get());
	}
	
	public void atualizarEndereco(Integer id, Endereco endereco) {
		Optional<Pessoa> pessoaSalva = buscarPessoaPeloCodigo(id);
		pessoaSalva.get().setEndereco(endereco);
		
		pessoaRepository.save(pessoaSalva.get());
	}
	
	private Optional<Pessoa> buscarPessoaPeloCodigo(Integer id) {
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(id);
		if(pessoaSalva.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
}
