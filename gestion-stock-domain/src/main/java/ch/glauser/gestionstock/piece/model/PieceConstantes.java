package ch.glauser.gestionstock.piece.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe contenant les constantes de texte pour les pièces
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PieceConstantes {
    public static final String FIELD_PIECE = "piece";

    public static final String FIELD_ID = "id";
    public static final String FIELD_NOM = "nom";
    public static final String FIELD_CATEGORIE = "categorie";
    public static final String FIELD_NUMERO_INVENTAIRE = "numeroInventaire";
    public static final String FIELD_PRIX = "prix";
    public static final String FIELD_QUANTITE = "quantite";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";
    public static final String FIELD_SEARCH_VALUE = "searchValue";

    public static final String ERROR_SUPPRESSION_PIECE_INEXISTANTE = "Impossible de supprimer cette pièce car elle n'existe pas";
    public static final String ERROR_SUPPRESSION_PIECE_IMPOSSIBLE_EXISTE_MACHINE = "Impossible de supprimer cette pièce car il existe une machine liée";

    public static final String ERROR_PIECE_NUMERO_INVENTAIRE_UNIQUE = "Le numéro d'inventaire de la pièce doit être unique";
    public static final String ERROR_CATEGORIE_DOIT_ETRE_ACTIVE = "La catégorie doit être active";
}