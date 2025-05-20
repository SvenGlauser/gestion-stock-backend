package ch.glauser.gestionstock.machine.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe contenant les constantes de texte pour les machines
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MachineConstantes {
    public static final String FIELD_MACHINE = "machine";

    public static final String FIELD_ID = "id";
    public static final String FIELD_NOM = "nom";

    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    public static final String ERROR_MACHINE_NOM_UNIQUE = "Le nom de la machine doit être unique";
}
