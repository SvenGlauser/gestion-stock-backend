package ch.glauser.gestionstock.pays.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.NotEmpty;
import ch.glauser.gestionstock.common.validation.Validator;
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
    private String abbreviation;

    @Override
    public void validate() {
        Validator.validate(this, Pays.class);
    }
}
