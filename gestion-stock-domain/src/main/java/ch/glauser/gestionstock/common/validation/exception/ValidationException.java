package ch.glauser.gestionstock.common.validation.exception;

import ch.glauser.gestionstock.common.validation.common.Error;
import lombok.Getter;

import java.util.List;

/**
 * Exception de validation
 */
@Getter
public final class ValidationException extends RuntimeException {
    private final transient List<Error> errors;

    /**
     * Ajout d'une erreur
     *
     * @param error Erreur d'utilisation de l'API
     */
    public ValidationException(Error error) {
        this.errors = List.of(error);
    }

    /**
     * Ajout d'une liste d'erreurs
     *
     * @param errors Erreurs d'utilisation de l'API
     */
    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
