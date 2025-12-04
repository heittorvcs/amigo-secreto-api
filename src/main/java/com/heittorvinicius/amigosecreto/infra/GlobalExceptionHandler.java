package com.heittorvinicius.amigosecreto.infra;

import com.heittorvinicius.amigosecreto.dto.ErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroDTO> handleResponseStatusException(ResponseStatusException ex) {
        String mensagem = ex.getReason(); 
        int status = ex.getStatusCode().value();
        
        ErroDTO erro = new ErroDTO(mensagem, status);
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> handleGenericException(Exception ex) {
        ex.printStackTrace(); 
        
        ErroDTO erro = new ErroDTO("Ocorreu um erro interno no servidor. Contate o suporte.", 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}