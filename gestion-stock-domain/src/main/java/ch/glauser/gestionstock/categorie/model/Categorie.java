package ch.glauser.gestionstock.categorie.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.NotEmpty;
import ch.glauser.gestionstock.common.validation.Validator;
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
    private boolean actif;

    @Override
    public void validate() {
        Validator.validate(this, Categorie.class);
    }
}
