package ch.glauser.gestionstock.categorie.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.notempty.NotEmpty;
import ch.glauser.gestionstock.validation.notnull.NotNull;
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
    public Validation validateChild() {
        return Validation.validate(this, Categorie.class);
    }
}
