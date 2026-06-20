package edu.utvt.alumnos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CorreoElectronicoAlreadyExistsException extends RuntimeException {
    public CorreoElectronicoAlreadyExistsException(String message) {
        super(message);
    }
}
