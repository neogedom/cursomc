package com.viniciusgomes.cursomc.resources.exceptions;

import com.viniciusgomes.cursomc.services.exceptions.DataIntegrityException;
import com.viniciusgomes.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
       // StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        StandardError err = StandardError.Builder.newBuilder()
                .timeStamp(ZonedDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail(e.getMessage())
                .build();

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
        // StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        StandardError err = StandardError.Builder.newBuilder()
                .timeStamp(ZonedDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Operation Prohibited")
                .detail(e.getMessage())
                .build();

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
       ValidationError err = new ValidationError("Erro de validação", HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());

       for (FieldError error: e.getBindingResult().getFieldErrors()) {
           err.addError(error.getField(), error.getDefaultMessage());
       }

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
