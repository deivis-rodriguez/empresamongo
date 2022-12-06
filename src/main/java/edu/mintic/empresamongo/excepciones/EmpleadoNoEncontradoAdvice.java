package edu.mintic.empresamongo.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmpleadoNoEncontradoAdvice {

    @ResponseBody
    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String empleadoNoEncontradoManejador(EmpleadoNoEncontradoException ex){
        return ex.getMessage();
    }
    
}
