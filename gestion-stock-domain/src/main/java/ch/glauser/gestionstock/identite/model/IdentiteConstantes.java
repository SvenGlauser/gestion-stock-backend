package ch.glauser.gestionstock.identite.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe contenant les constantes de texte pour les identités
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IdentiteConstantes {
    public static final String FIELD_IDENTITE = "identité";

    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    public static final String ERROR_SUPPRESSION_IDENTITE_IMPOSSIBLE_EXISTE_MACHINE = "Impossible de supprimer cette identité car il existe une machine liée";
}