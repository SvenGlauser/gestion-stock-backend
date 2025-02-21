package ch.glauser.gestionstock.localite.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.notempty.NotEmpty;
import ch.glauser.gestionstock.common.validation.notnull.NotNull;
import ch.glauser.gestionstock.pays.model.Pays;
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
    public Validator validateChild() {
        return Validator.validate(this, Localite.class);
    }
}
