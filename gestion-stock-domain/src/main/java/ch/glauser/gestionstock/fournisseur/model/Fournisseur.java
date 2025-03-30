package ch.glauser.gestionstock.fournisseur.model;

import ch.glauser.gestionstock.adresse.model.Adresse;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.cascade.CascadeValidation;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.notempty.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model représentant un fournisseur
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Fournisseur extends Model {
    @NotEmpty
    private String nom;
    private String description;

    private String url;

    @CascadeValidation
    private Adresse adresse;

    @Override
    public Validation validateChild() {
        return Validation.validate(this, Fournisseur.class);
    }
}
