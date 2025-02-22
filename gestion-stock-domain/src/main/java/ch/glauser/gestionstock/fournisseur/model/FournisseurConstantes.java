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
    public static final String FIELD_NOM = "nom";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    public static final String ERROR_SUPPRESSION_FOURNISSEUR_INEXISTANTE = "Impossible de supprimer ce fournisseur car il n'existe pas";
    public static final String ERROR_SUPPRESSION_FOURNISSEUR_IMPOSSIBLE_EXISTE_CONTACT = "Impossible de supprimer ce fournisseur car il existe une pièce liée";

    public static final String ERROR_FOURNISSEUR_NOM_UNIQUE = "Le nom de la catégorie doit être unique";
}