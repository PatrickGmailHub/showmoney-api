package br.showmoney.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.showmoney.api.event.RecursoCriadoEvent;
import br.showmoney.api.exceptionhandler.ShowMoneyResponseEntityExceptionHandler.Erro;
import br.showmoney.api.model.Lancamento;
import br.showmoney.api.repository.LancamentoRepository;
import br.showmoney.api.repository.filter.LancamentoFilter;
import br.showmoney.api.service.LancamentoService;
import br.showmoney.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publish;
	
	@GetMapping
	public List<Lancamento> listar() {
		return lancamentoRepository.findAll();
	}

	@GetMapping("filtrar")
	public List<Lancamento> pesquisar(LancamentoFilter filter) {
		return lancamentoRepository.filtrar(filter);
	}

	@GetMapping("filtrar-paginado")
	public Page<Lancamento> pesquisarPaginado(LancamentoFilter filter, Pageable pageable) {
		return lancamentoRepository.filtrarPaginado(filter, pageable);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Lancamento> buscarPeloId(@PathVariable Long id) {
		return lancamentoRepository.findById(id)
				.map(lancamento -> ResponseEntity.ok(lancamento))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
		
		publish.publishEvent(new RecursoCriadoEvent(this, response, lancamento.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	@DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  private void remover(@PathVariable Long id) {
		lancamentoRepository.deleteById(id);
	}
	
	@ExceptionHandler(PessoaInexistenteOuInativaException.class)
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {

		//String mensagemUsuario = messageSouce.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		//String mensagemDesenvolvedor = ex.getCause() == null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = ex.getMessage();
		String mensagemDesenvolvedor = ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
}
