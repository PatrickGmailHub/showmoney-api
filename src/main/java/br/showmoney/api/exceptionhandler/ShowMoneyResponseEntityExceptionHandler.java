package br.showmoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ControllerAdvice
public class ShowMoneyResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSouce;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String mensagemUsuario = messageSouce.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() == null ? ex.getCause().toString() : ex.toString();
		
		//return handleExceptionInternal(ex, mensagemUsuario, headers, HttpStatus.BAD_REQUEST, request);
		//return handleExceptionInternal(ex,  new Erro(mensagemUsuario, mensagemDesenvolvedor), headers, HttpStatus.BAD_REQUEST, request);
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
		
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		
		String mensagemUsuario = messageSouce.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		
		String mensagemUsuario = messageSouce.getMessage("recurso.operacao-nao-premitida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);//ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();
		
		/*
		 * for(FieldError fieldError : bindingResult.getFieldErrors()) { String
		 * mensagemUsuario = messageSouce.getMessage(fieldError,
		 * LocaleContextHolder.getLocale()); String mensagemDesenvolvedor =
		 * fieldError.toString(); erros.add(new Erro(mensagemUsuario,
		 * mensagemDesenvolvedor)); }
		 */
		
		bindingResult.getFieldErrors()
			.forEach((erro) -> erros.add(new Erro(messageSouce.getMessage(erro, LocaleContextHolder.getLocale()), erro.toString())));
		
		return erros;
	}
	
	@Getter
	@AllArgsConstructor
	public static class Erro {
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;		
	}
	
}
