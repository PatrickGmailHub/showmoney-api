package br.showmoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.showmoney.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
