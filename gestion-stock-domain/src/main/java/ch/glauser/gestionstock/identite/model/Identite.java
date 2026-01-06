package ch.glauser.gestionstock.identite.model;

import ch.glauser.gestionstock.adresse.model.Adresse;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.validation.cascade.CascadeValidation;
import ch.glauser.validation.maxlength.MaxLength;
import ch.glauser.validation.regex.Regex;
import ch.glauser.validation.regex.RegexValidationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Identite extends Model {
    @MaxLength(255)
    @Regex(RegexValidationType.EMAIL)
    private String email;
    @MaxLength(255)
    @Regex(RegexValidationType.PHONE_NUMBER)
    private String telephone;

    @CascadeValidation
    private Adresse adresse;

    @MaxLength(255)
    private String remarques;

    public abstract String designation();
}
