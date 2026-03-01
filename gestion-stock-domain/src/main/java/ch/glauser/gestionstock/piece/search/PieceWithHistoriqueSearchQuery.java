package ch.glauser.gestionstock.piece.search;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.searchquery.api.SearchQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PieceWithHistoriqueSearchQuery extends SearchQuery {
    private SearchField<String> numeroInventaire;
    private SearchField<String> nom;
    private SearchField<Long> categorieId;
    private SearchField<String> categorieNom;
    private SearchField<Long> prix;
    private SearchField<Long> quantite;

    private SearchField<LocalDate> dateDebut;
    private SearchField<LocalDate> dateFin;
    private SearchField<Integer> quantiteEntree;
    private SearchField<Integer> quantiteSortie;
}
