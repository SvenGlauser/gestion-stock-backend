package ch.glauser.gestionstock.piece.search;

import ch.glauser.filters.api.field.Field;
import ch.glauser.filters.api.search.SearchQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PieceSearchQuery extends SearchQuery {
    private Field<String> numeroInventaire;
    private Field<String> nom;
    private Field<Long> categorieId;
    private Field<Long> prix;
    private Field<Long> quantite;
}
