package ch.glauser.gestionstock.pays.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.notempty.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model représentant un pays
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Pays extends Model {
    @NotEmpty
    private String nom;
    @NotEmpty
    private String abreviation;

    @Override
    public Validator validateChild() {
        return Validator.validate(this, Pays.class);
    }
}
