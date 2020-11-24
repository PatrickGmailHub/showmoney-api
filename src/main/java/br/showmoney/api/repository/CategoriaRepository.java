package br.showmoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.showmoney.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
