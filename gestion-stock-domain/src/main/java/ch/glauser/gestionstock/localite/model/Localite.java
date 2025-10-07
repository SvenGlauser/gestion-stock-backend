package ch.glauser.gestionstock.localite.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.notempty.NotEmpty;
import ch.glauser.validation.notnull.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model représentant une localité
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Localite extends Model {
    @NotEmpty
    private String nom;
    @NotEmpty
    private String npa;
    @NotNull
    private Pays pays;

    @Override
    public Validation validateChild() {
        return Validation.validate(this, Localite.class);
    }
}
