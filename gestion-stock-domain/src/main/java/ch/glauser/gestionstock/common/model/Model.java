package ch.glauser.gestionstock.common.model;

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
    public abstract void validate();
}
