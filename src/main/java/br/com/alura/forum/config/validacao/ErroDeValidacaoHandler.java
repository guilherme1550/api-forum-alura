package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {

		// Cria uma lista de erros que sera retornado ao cliente.
		List<ErroDeFormularioDto> dto = new ArrayList<>();

		// Captura todos os erros dos campos
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		fieldErrors.forEach(e -> {
			// Captura a mensagem do erro e traduz para o idioma do cliente
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
			dto.add(erro);
		});

		return dto;
	}

	// Esse erro acontece no momento de deletar um Topico na classe
	// TopicosController, caso coloque um Id que não exista.
	// Esse erro pode ser tratado no próprio método remover da Classe
	// TopicosController ou pode ser tratado pelo método abaixo
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> handle(EmptyResultDataAccessException exception) {
		return ResponseEntity.notFound().build();
	}
}
