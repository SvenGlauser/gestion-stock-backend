package ch.glauser.gestionstock.identite.model;

import ch.glauser.gestionstock.adresse.model.Adresse;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.validation.cascade.CascadeValidation;
import ch.glauser.gestionstock.validation.regex.Regex;
import ch.glauser.gestionstock.validation.regex.RegexValidationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Identite extends Model {
    @Regex(RegexValidationType.EMAIL)
    private String email;
    @Regex(RegexValidationType.PHONE_NUMBER)
    private String telephone;

    @CascadeValidation
    private Adresse adresse;

    private String remarques;

    public abstract String designation();
}
