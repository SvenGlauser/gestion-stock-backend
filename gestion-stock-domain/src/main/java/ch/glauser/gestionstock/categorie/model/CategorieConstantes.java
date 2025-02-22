package ch.glauser.gestionstock.categorie.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe contenant les constantes de texte pour les catégories
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CategorieConstantes {
    public static final String FIELD_CATEGORIE = "categorie";

    public static final String FIELD_ID = "id";
    public static final String FIELD_NOM = "nom";

    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    public static final String ERROR_SUPPRESSION_CATEGORIE_INEXISTANTE = "Impossible de supprimer cette catégorie car elle n'existe pas";
    public static final String ERROR_SUPPRESSION_CATEGORIE_IMPOSSIBLE_EXISTE_PIECE = "Impossible de supprimer cette catégorie car il existe une pièce liée";

    public static final String ERROR_CATEGORIE_NOM_UNIQUE = "Le nom de la catégorie doit être unique";
}