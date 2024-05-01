package com.velazquez.proyectointegrado.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(org.hibernate.PropertyValueException.class)
    public ResponseEntity<String> handlePropertyValueException(org.hibernate.PropertyValueException ex) {
        String fieldName = ex.getPropertyName(); // Obtiene el nombre del campo que causó el error
        String errorMessage = "El campo '" + fieldName + "' es obligatorio.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
