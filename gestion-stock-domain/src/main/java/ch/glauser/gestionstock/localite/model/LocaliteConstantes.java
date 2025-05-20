package ch.glauser.gestionstock.localite.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe contenant les constantes de texte pour les localités
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocaliteConstantes {
    public static final String FIELD_LOCALITE = "localite";

    public static final String FIELD_ID = "id";
    public static final String FIELD_NOM = "nom";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    public static final String ERROR_SUPPRESSION_LOCALITE_IMPOSSIBLE_EXISTE_IDENTITE = "Impossible de supprimer cette localité car il existe une identité liée";
    public static final String ERROR_SUPPRESSION_LOCALITE_IMPOSSIBLE_EXISTE_FOURNISSEUR = "Impossible de supprimer cette localité car il existe un fournisseur lié";

    public static final String ERROR_LOCALITE_NOM_UNIQUE = "L'association NPA - nom doit être unique pour le pays";
}