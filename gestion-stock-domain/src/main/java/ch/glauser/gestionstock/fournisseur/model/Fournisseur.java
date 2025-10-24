package ch.glauser.gestionstock.fournisseur.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.identite.model.Identite;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.notnull.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model représentant un fournisseur
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Fournisseur extends Model {
    @NotNull
    private Identite identite;

    private String description;
    private String url;

    @Override
    public Validation validateChild() {
        return Validation.validate(this, Fournisseur.class);
    }
}
