package ch.glauser.gestionstock.contact.model;

import ch.glauser.gestionstock.adresse.model.Adresse;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.CascadeValidation;
import ch.glauser.gestionstock.common.validation.NotEmpty;
import ch.glauser.gestionstock.common.validation.NotNull;
import ch.glauser.gestionstock.common.validation.Validator;
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
