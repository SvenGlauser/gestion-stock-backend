package ch.glauser.gestionstock.common.model;

import ch.glauser.gestionstock.common.validation.common.Validator;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Model de base
 */
@Data
public abstract class Model {
    private Long id;
    private String creationUser;
    private LocalDateTime creationDate;
    private String modificationUser;
    private LocalDateTime modificationDate;

    /**
     * Valide un model
     */
    public void validate() {
        this.validateChild().execute();
    }

    /**
     * Appelle une méthode de validation pour la création
     */
    public Validator validateCreate() {
        return this.validateChild()
            .validateIsNull(this.id, "id");
    }

    /**
     * Appelle une méthode de validation pour la modification
     */
    public Validator validateModify() {
        return this.validateChild()
            .validateNotNull(this.id, "id");
    }

    /**
     * Appelle une méthode de validation de l'enfant
     */
    protected abstract Validator validateChild();
}
