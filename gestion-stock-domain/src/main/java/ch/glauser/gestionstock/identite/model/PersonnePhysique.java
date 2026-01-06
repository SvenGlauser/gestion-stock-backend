package ch.glauser.gestionstock.identite.model;

import ch.glauser.validation.common.Validation;
import ch.glauser.validation.maxlength.MaxLength;
import ch.glauser.validation.notempty.NotEmpty;
import ch.glauser.validation.notnull.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model représentant une personne physique
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonnePhysique extends Identite {
    @NotNull
    private Titre titre;
    @NotEmpty
    @MaxLength(255)
    private String nom;
    @NotEmpty
    @MaxLength(255)
    private String prenom;

    @Override
    public Validation validateChild() {
        return Validation.validate(this, PersonnePhysique.class);
    }

    @Override
    public String designation() {
        return this.prenom + " " + this.nom;
    }
}
