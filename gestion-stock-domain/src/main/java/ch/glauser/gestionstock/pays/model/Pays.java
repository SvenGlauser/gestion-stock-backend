package ch.glauser.gestionstock.pays.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.maxlength.MaxLength;
import ch.glauser.validation.notempty.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model représentant un pays
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Pays extends Model {
    @NotEmpty
    @MaxLength(255)
    private String nom;
    @NotEmpty
    @MaxLength(255)
    private String abreviation;

    @Override
    public Validation validateChild() {
        return Validation.validate(this, Pays.class);
    }
}
