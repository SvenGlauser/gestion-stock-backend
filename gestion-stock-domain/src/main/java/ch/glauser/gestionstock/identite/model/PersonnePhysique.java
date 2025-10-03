package ch.glauser.gestionstock.identite.model;

import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.notempty.NotEmpty;
import ch.glauser.gestionstock.validation.notnull.NotNull;
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
    private String nom;
    @NotEmpty
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
