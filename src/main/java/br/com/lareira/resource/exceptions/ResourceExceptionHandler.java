package br.com.lareira.resource.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;

import br.com.lareira.service.exceptions.AuthorizationException;
import br.com.lareira.service.exceptions.BadRequestIdException;
import br.com.lareira.service.exceptions.DataIntegrityException;
import br.com.lareira.service.exceptions.FileException;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Classe criada na aula https://www.udemy.com/course/spring-boot-ionic/learn/lecture/8090544#overview
// Serve para ser compartilhada entre todos os resources e devolver uma mensagem de erro para os chamadores REST
@ControllerAdvice
public class ResourceExceptionHandler {

    // @Autowired
	// private MessageSource messageSource;

    /**
     * Este método é o que vai ouvir e responder quando essa exceção ObjectNotFoundException foi chamada
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        // Chamo a minha classe padrão dos erros
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err); // Retorna o response com o status 404 e o corpo da mensagem com o erro apresentado na mensagem
    }

    /**
     * Este método é o que vai ouvir e responder quando essa exceção DataIntegrityException foi chamada
     * https://www.udemy.com/course/spring-boot-ionic/learn/lecture/8169642#overview
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
        // Chamo a minha classe padrão dos erros
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); // Retorna o response com o status 404 e o corpo da mensagem com o erro apresentado na mensagem
    }

    /**
     * Será invocada quando ocorrer algum erro de validação das classes @Valid    
     * https://github.com/trentinrossi/springboot2-ionic-backend/commit/c94ece4ff848655d46bad861889d7332e9af885f
     * https://www.udemy.com/course/spring-boot-ionic/learn/lecture/8186080#overview
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());            
		}		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    /**
     * Será invocada quando ocorrer algum erro de validação das classes @Valid    
     * https://github.com/trentinrossi/springboot2-ionic-backend/commit/c94ece4ff848655d46bad861889d7332e9af885f
     * https://www.udemy.com/course/spring-boot-ionic/learn/lecture/8186080#overview
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> validation(ConstraintViolationException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());        
        
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            err.addError(violation.getPropertyPath().toString(), violation.getMessage());
        }	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    /**
     * Será invocada quando ocorrer alguma divergência nos ID's das entidades sendo passapara para INSERT ou UPDATE 
     * Exemplo tirado do jHipster
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BadRequestIdException.class)
	public ResponseEntity<StandardError> badRequestId(BadRequestIdException e, HttpServletRequest request) {		
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorizarionException(AuthorizationException e, HttpServletRequest request) {
        // Chamo a minha classe padrão dos erros
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err); // Retorna o response com o status 404 e o corpo da mensagem com o erro apresentado na mensagem
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request) {
        // Chamo a minha classe padrão dos erros
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); // Retorna o response com o status 404 e o corpo da mensagem com o erro apresentado na mensagem
    }

    @ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request) {

		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		StandardError err = new StandardError(code.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(code).body(err);
	}

	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}