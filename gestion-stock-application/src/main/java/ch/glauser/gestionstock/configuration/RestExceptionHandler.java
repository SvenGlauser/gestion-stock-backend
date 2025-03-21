package ch.glauser.gestionstock.configuration;

import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class RestExceptionHandler  {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<List<Error>> handleConflict(ValidationException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getErrors());
        // TODO logger l'erreur avec la stack
    }
}
