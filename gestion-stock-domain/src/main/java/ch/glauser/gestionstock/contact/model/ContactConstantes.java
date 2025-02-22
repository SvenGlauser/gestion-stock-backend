package ch.glauser.gestionstock.contact.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe contenant les constantes de texte pour les contacts
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactConstantes {
    public static final String FIELD_CONTACT = "contact";

    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    public static final String ERROR_SUPPRESSION_CONTACT_INEXISTANTE = "Impossible de supprimer ce contact car il n'existe pas";
    public static final String ERROR_SUPPRESSION_CONTACT_IMPOSSIBLE_EXISTE_MACHINE = "Impossible de supprimer ce contact car il existe une machine liée";
}