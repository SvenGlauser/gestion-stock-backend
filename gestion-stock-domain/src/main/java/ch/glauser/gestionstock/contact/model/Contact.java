package ch.glauser.gestionstock.contact.model;

import ch.glauser.gestionstock.adresse.model.Adresse;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.cascade.CascadeValidation;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.notempty.NotEmpty;
import ch.glauser.gestionstock.common.validation.notnull.NotNull;
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

    private String email;
    private String telephone;

    @CascadeValidation
    private Adresse adresse;

    private String remarques;

    @Override
    public Validator validateChild() {
        return Validator.validate(this, Contact.class);
    }
}
