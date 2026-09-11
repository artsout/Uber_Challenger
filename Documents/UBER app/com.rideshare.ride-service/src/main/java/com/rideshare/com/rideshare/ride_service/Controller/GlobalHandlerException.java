package com.rideshare.com.rideshare.ride_service.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class GlobalHandlerException {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public  ResponseEntity<Map<String,String>> handleValidationException(
            MethodArgumentNotValidException ex
    ){
        Map<String,String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> erros.put(fieldError.getField(),fieldError.getDefaultMessage()));


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler({RuntimeException.class})
        public  ResponseEntity<Map<String,String>> handleRuntimeException(
                RuntimeException ex
        ){
        log.error("Runtime Exception : {} ", ex.getMessage());
            Map<String,String> erros =new HashMap<>();

            erros.put("error ", ex.getMessage());

            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
        }
    }


