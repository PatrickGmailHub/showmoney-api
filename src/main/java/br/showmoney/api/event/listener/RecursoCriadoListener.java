package br.showmoney.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.showmoney.api.event.RecursoCriadoEvent;

public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	@Override
	public void onApplicationEvent(RecursoCriadoEvent event) {
		HttpServletResponse response = event.getResponse();
		Long codigo = event.getCodigo();
		
		adicionarHeaderLocation(response, codigo);
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
	  		.buildAndExpand(codigo).toUri();
	  	
  	response.setHeader("Location", uri.toASCIIString());
	}
	
}
