package ch.glauser.gestionstock.identite.model;

import ch.glauser.validation.common.Validation;
import ch.glauser.validation.notnull.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model représentant une personne morale
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonneMorale extends Identite {
    @NotNull
    private String raisonSociale;

    // TODO Implémenter des contacts

    @Override
    public Validation validateChild() {
        return Validation.validate(this, PersonneMorale.class);
    }

    @Override
    public String designation() {
        return this.raisonSociale;
    }
}
