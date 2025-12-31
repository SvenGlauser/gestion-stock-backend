package ch.glauser.gestionstock.configuration;

import ch.glauser.gestionstock.exception.service.ThrownExceptionService;
import ch.glauser.validation.common.Error;
import ch.glauser.validation.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.List;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler  {

    private final ThrownExceptionService thrownExceptionService;

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleConflict(Exception e, HttpServletRequest request) {
        log.error(e.getClass().getSimpleName(), e);

        this.thrownExceptionService.createException(e);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail(e.getMessage());
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

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<String> handleConflict(AuthorizationDeniedException e) {
        this.thrownExceptionService.createException(e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
