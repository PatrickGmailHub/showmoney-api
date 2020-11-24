package br.showmoney.api.resource;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.showmoney.api.event.RecursoCriadoEvent;
import br.showmoney.api.model.Categoria;
import br.showmoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
  @GetMapping
  public List<Categoria> listar() {
  	return categoriaRepository.findAll(); 
  }
  
  @GetMapping("{codigo}")
  public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Integer codigo) {
  	return categoriaRepository.findById(codigo)
			.map(categoria -> ResponseEntity.ok(categoria))
			.orElse(ResponseEntity.notFound().build());
  }
  
  @PostMapping
  public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
  	Categoria categoriaSalva = categoriaRepository.save(categoria);
  	
		/*
		 * URI uri =
		 * ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
		 * .buildAndExpand(categoriaSalva.getCodigo()).toUri();
		 * 
		 * response.setHeader("Location", uri.toASCIIString());
		 * 
		 * return ResponseEntity.created(uri).body(categoriaSalva);
		 */
  		
  		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo().longValue()));
  		
  		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
  }
  
  @DeleteMapping("{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  private void remover(@PathVariable (value = "codigo") Integer id) {
		categoriaRepository.deleteById(id);
	}
    
	/*
	 * @PostMapping
	 * 
	 * @ResponseStatus(HttpStatus.CREATED) public void criar(@RequestBody Categoria
	 * categoria) { categoriaRepository.save(categoria); }
	 */	 

	/*
	 * @GetMapping public ResponseEntity<?> listar() { List<Categoria> categorias =
	 * categoriaRepository.findAll(); return !categorias.isEmpty() ?
	 * ResponseEntity.ok(categorias) : ResponseEntity.noContent().build(); }
	 */
	 

}
