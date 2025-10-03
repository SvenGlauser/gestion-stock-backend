package ch.glauser.gestionstock.exception.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.notempty.NotEmpty;
import ch.glauser.gestionstock.validation.notnull.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Classe représentant une erreur précédemment lancée
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuppressWarnings("java:S2166")
public class ThrownException extends Model {
    @NotEmpty
    private String stacktrace;
    @NotEmpty
    private String className;
    private String message;
    @NotNull
    private LocalDateTime timestamp;
    @NotEmpty
    private boolean actif;

    @Override
    protected Validation validateChild() {
        return Validation.of(ThrownException.class);
    }
}
