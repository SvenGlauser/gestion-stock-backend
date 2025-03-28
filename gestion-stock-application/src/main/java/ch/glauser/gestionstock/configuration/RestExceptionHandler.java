package ch.glauser.gestionstock.configuration;

import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.exception.service.ThrownExceptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler  {

    private final ThrownExceptionService thrownExceptionService;

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleConflict(Exception e, HttpServletRequest request) {
        this.thrownExceptionService.createException(e);
        e.printStackTrace();

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty("exception", e.getClass().getSimpleName()); // Nom de l'exception
        problemDetail.setProperty("path", request.getRequestURI()); // Endpoint concerné

        // Définir le type sur null (éventuellement non affiché selon la sérialisation)
        problemDetail.setType(URI.create(""));

        return problemDetail;
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<List<Error>> handleConflict(ValidationException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getErrors());
    }
}
