package ch.glauser.gestionstock.contact.model;

import ch.glauser.gestionstock.adresse.model.Adresse;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.cascade.CascadeValidation;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.notempty.NotEmpty;
import ch.glauser.gestionstock.common.validation.notnull.NotNull;
import ch.glauser.gestionstock.common.validation.regex.Regex;
import ch.glauser.gestionstock.common.validation.regex.RegexValidationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model représentant un contact
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Contact extends Model {
    @NotNull
    private Titre titre;
    @NotEmpty
    private String nom;
    @NotEmpty
    private String prenom;

    @Regex(RegexValidationType.EMAIL)
    private String email;
    @Regex(RegexValidationType.PHONE_NUMBER)
    private String telephone;

    @CascadeValidation
    private Adresse adresse;

    private String remarques;

    @Override
    public Validation validateChild() {
        return Validation.validate(this, Contact.class);
    }
}
