package br.showmoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.showmoney.api.event.RecursoCriadoEvent;
import br.showmoney.api.model.Endereco;
import br.showmoney.api.model.Pessoa;
import br.showmoney.api.repository.PessoaRepository;
import br.showmoney.api.service.PessoaService;

@RestController
@RequestMapping("pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
  @GetMapping
  public List<Pessoa> listar() {
  	return pessoaRepository.findAll(); 
  }
  
  @GetMapping("{id}")
  public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Integer id) {
  	return pessoaRepository.findById(id)
			.map(pessoa -> ResponseEntity.ok(pessoa))
			.orElse(ResponseEntity.notFound().build());
  }
  
  @PostMapping
  public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
  	Pessoa pessoaSalva = pessoaRepository.save(pessoa);
  	
  	publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId().longValue()));
	  	
  	return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
  }
  
  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  private void remover(@PathVariable Integer id) {
		pessoaRepository.deleteById(id);
	}
  
  @PutMapping("{id}")
  public ResponseEntity<Pessoa> atualizar(@PathVariable Integer id, @Valid @RequestBody Pessoa pessoa) {
  	Pessoa pessaoSalva = pessoaService.atualizar(id, pessoa);
  	
  	return ResponseEntity.ok(pessaoSalva);
  }
  
  @PutMapping("{id}/ativo")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void atualizarPropriedadeativo(@PathVariable Integer id, @RequestBody Boolean ativo) {
  	pessoaService.atualizarPropriedadeAtivo(id, ativo);
  }
  
  @PutMapping("{id}/endereco")
  public ResponseEntity<Pessoa> atualizarEndereco(@PathVariable Integer id, @Valid @RequestBody Endereco endereco) {
  	pessoaService.atualizarEndereco(id, endereco);
  	Optional<Pessoa> pessoaAtualizada = pessoaRepository.findById(id);
  	
  	return ResponseEntity.ok(pessoaAtualizada.get());
  }

}
