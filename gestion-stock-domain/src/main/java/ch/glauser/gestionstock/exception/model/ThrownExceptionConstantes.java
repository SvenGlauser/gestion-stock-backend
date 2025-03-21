package ch.glauser.gestionstock.exception.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe contenant les constantes de texte pour les exceptions
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThrownExceptionConstantes {
    public static final String FIELD_ID = "id";
    public static final String FIELD_ACTIF = "actif";

    public static final String FIELD_SEARCH_REQUEST = "searchRequest";
    public static final String FIELD_EXCEPTION = "exception";
}