package ch.glauser.gestionstock.identite.dto;

import ch.glauser.gestionstock.validation.exception.TechnicalException;
import ch.glauser.gestionstock.identite.model.Identite;
import ch.glauser.gestionstock.identite.model.PersonneMorale;
import ch.glauser.gestionstock.identite.model.PersonnePhysique;

/**
 * Type d'identité
 */
public enum IdentiteType {
    PERSONNE_PHYSIQUE,
    PERSONNE_MORALE;

    public static IdentiteDto castToDto(Identite identite) {
        return switch (identite) {
            case PersonnePhysique personnePhysique -> new PersonnePhysiqueDto(personnePhysique);
            case PersonneMorale personneMorale -> new PersonneMoraleDto(personneMorale);
            default -> throw new TechnicalException("Impossible de reconnaitre le type d'identité");
        };
    }

    public static IdentiteType getType(Identite identite) {
        return switch (identite) {
            case PersonnePhysique ignored -> PERSONNE_PHYSIQUE;
            case PersonneMorale ignored -> PERSONNE_MORALE;
            default -> throw new TechnicalException("Impossible de reconnaitre le type d'identité");
        };
    }
}
