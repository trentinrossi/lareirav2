package br.com.lareira.resource.exceptions;

import java.util.ArrayList;
import java.util.List;

// Classe para ser instanciada dentro de ResourceExceptionHandler a ser apresetada quando ocorrer algum erro de validação @Valid
// https://github.com/trentinrossi/springboot2-ionic-backend/commit/c94ece4ff848655d46bad861889d7332e9af885f
// https://www.udemy.com/course/spring-boot-ionic/learn/lecture/8186080#overview
public class ValidationError extends StandardError {
    private static final long serialVersionUID = 1L;

    private List<FieldMessage> errors = new ArrayList<>();


    public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}    

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String messagem) {
        errors.add(new FieldMessage(fieldName, messagem));
    }
}