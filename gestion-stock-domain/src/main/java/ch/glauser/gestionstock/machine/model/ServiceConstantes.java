package ch.glauser.gestionstock.machine.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe contenant les constantes de texte pour les services
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServiceConstantes {
    public static final String FIELD_SERVICE = "service";

    public static final String FIELD_ID = "id";
    public static final String FIELD_ID_MACHINE = "idMachine";
    public static final String FIELD_MACHINE = "machine";

    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    public static final String ERROR_IMPOSSIBLE_CHANGER_MACHINE_SERVICE = "Impossible de changer la machine liée à un service";
}
