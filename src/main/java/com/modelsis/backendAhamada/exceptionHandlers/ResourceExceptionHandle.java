package com.modelsis.backendAhamada.exceptionHandlers;

import com.modelsis.backendAhamada.Exception.ProductNotFoundException;
import com.modelsis.backendAhamada.Exception.ProductTypeNotFoundException;
import com.modelsis.backendAhamada.utils.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class ResourceExceptionHandle extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> ProductNotFoundException(ProductNotFoundException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(ProductTypeNotFoundException.class)
    public ResponseEntity<ErrorMessage> ProductTypeNotFoundException(ProductNotFoundException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
