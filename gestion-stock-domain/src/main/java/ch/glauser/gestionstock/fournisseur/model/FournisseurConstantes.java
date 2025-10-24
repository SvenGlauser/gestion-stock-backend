package ch.glauser.gestionstock.fournisseur.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe contenant les constantes de texte pour les fournisseurs
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FournisseurConstantes {
    public static final String FIELD_FOURNISSEUR = "fournisseur";

    public static final String FIELD_ID = "id";
    public static final String FIELD_IDENTITE = "identite";
    public static final String FIELD_DESIGNATION = "designation";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    public static final String ERROR_SUPPRESSION_FOURNISSEUR_IMPOSSIBLE_EXISTE_PIECE = "Impossible de supprimer ce fournisseur car il existe une pièce liée";

    public static final String ERROR_FOURNISSEUR_IDENTITE_UNIQUE = "L'identité du fournisseur doit être unique";
}