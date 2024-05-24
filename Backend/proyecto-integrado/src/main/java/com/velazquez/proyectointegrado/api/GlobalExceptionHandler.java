package com.velazquez.proyectointegrado.api;

import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "Error: El usuario ya está registrado.";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
