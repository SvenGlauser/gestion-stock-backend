package ch.glauser.gestionstock.piece.search;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.searchquery.api.SearchQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PieceSearchQuery extends SearchQuery {
    private SearchField<String> numeroInventaire;
    private SearchField<String> nom;
    private SearchField<Long> categorieId;
    private SearchField<Long> prix;
    private SearchField<Long> quantite;
}
