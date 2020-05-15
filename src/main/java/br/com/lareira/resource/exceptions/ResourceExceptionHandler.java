package br.com.lareira.resource.exceptions;

import javax.servlet.http.HttpServletRequest;

import br.com.lareira.service.exceptions.ObjectNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Classe criada na aula https://www.udemy.com/course/spring-boot-ionic/learn/lecture/8090544#overview
// Serve para ser compartilhada entre todos os resources e devolver uma mensagem de erro para os chamadores REST
@ControllerAdvice
public class ResourceExceptionHandler {

    // Este método é o que vai ouvir e responder quando essa exceção ObjectNotFoundException foi chamada
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        // Chamo a minha classe padrão dos erros
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err); // Retorna o response com o status 404 e o corpo da mensagem com o erro apresentado na mensagem
    }

}