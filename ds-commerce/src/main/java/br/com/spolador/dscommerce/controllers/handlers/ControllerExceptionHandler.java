package br.com.spolador.dscommerce.controllers.handlers;

import br.com.spolador.dscommerce.dto.CustomError;
import br.com.spolador.dscommerce.dto.ValidationError;
import br.com.spolador.dscommerce.services.exceptions.DatabaseException;
import br.com.spolador.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound (ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND; // retorna 404
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());  // .value passa para inteiro
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database (DatabaseException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST; // erro genérico 400
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());  // .value passa para inteiro
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid (MethodArgumentNotValidException e, HttpServletRequest request){ // CustomError é um super tipo ValidationError
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; // erro 422
        ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());  // .value passa para inteiro

        for (FieldError f : e.getBindingResult().getFieldErrors()){ // percorre todos os erros na lista e.getBindingResult().getFieldErrors() e apelidando-os de f
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

}
