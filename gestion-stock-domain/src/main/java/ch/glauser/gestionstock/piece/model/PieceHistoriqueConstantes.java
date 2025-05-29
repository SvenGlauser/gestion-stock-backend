package ch.glauser.gestionstock.piece.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe contenant les constantes de texte pour les mouvements de pièces
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PieceHistoriqueConstantes {
    public static final String FIELD_PIECE_HISTORIQUE = "pieceHistorique";
    public static final String FIELD_NEW_PIECE = "newPiece";
    public static final String FIELD_OLD_PIECE = "oldPiece";

    public static final String FIELD_ID = "id";
    public static final String FIELD_PIECE_ID = "pieceId";
    public static final String FIELD_DATE = "date";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_SOURCE = "source";

    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    public static final String ERROR_IMPOSSIBLE_SUPPRIMER_HISTORIQUE_PIECE_NON_DERNIER = "Impossible de supprimer un autre élément d'historique que le dernier";
}