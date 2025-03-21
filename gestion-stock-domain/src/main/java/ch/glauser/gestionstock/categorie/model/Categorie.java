package ch.glauser.gestionstock.categorie.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.notempty.NotEmpty;
import ch.glauser.gestionstock.common.validation.notnull.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model représentant une catégorie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Categorie extends Model {
    @NotEmpty
    private String nom;
    private String description;

    @NotNull
    private Boolean actif;

    @Override
    public Validator validateChild() {
        return Validator.validate(this, Categorie.class);
    }
}
