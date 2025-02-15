package ch.glauser.gestionstock.common.model;

import ch.glauser.gestionstock.common.validation.Validator;
import lombok.Data;

/**
 * Model de base
 */
@Data
public abstract class Model {
    private Long id;

    /**
     * Appelle une méthode de validation
     */
    public void validate() {
        this.validateChild().execute();
    }

    /**
     * Appelle une méthode de validation
     */
    public void validateModify() {
        this.validateChild()
            .validateNotNull(this.id, "id")
            .execute();
    }

    /**
     * Appelle une méthode de validation de l'enfant
     */
    protected abstract Validator validateChild();
}
