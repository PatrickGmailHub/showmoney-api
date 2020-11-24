package br.showmoney.api.service.exception;

@SuppressWarnings("serial")
public class PessoaInexistenteOuInativaException extends RuntimeException {
	
	public PessoaInexistenteOuInativaException(String msg) {
		super(msg);
	}
}
