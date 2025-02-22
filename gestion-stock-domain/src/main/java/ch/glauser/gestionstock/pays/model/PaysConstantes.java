package ch.glauser.gestionstock.pays.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe contenant les constantes de texte pour les pays
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaysConstantes {
    public static final String FIELD_PAYS = "pays";

    public static final String FIELD_ID = "id";
    public static final String FIELD_NOM = "nom";
    public static final String FIELD_ABREVIATION = "abreviation";

    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    public static final String ERROR_SUPPRESSION_PAYS_INEXISTANTE = "Impossible de supprimer ce pays car il n'existe pas";
    public static final String ERROR_SUPPRESSION_PAYS_IMPOSSIBLE_EXISTE_LOCALITE = "Impossible de supprimer ce pays car il existe une localité liée";

    public static final String ERROR_PAYS_NOM_UNIQUE = "Le nom du pays doit être unique";
    public static final String ERROR_PAYS_ABREVIATION_UNIQUE = "L'abréviation du pays doit être unique";
}