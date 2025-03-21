package ch.glauser.gestionstock.exception.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.common.Validator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe représentant une erreur précédemment lancée
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuppressWarnings("java:S2166")
public class ThrownException extends Model {
    private String stacktrace;
    private String className;
    private String message;
    private boolean actif;

    @Override
    protected Validator validateChild() {
        return Validator.of(ThrownException.class);
    }
}
