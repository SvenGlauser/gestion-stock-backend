package ch.glauser.filters.automatic;

import ch.glauser.filters.filter.api.SearchFieldCombinaisonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Combinaison de champ de recherche automatique
 */
@Getter
@Setter
@NoArgsConstructor
public class AutomaticSearchFieldCombinaison {
    private List<AutomaticSearchField<?>> fields;
    private SearchFieldCombinaisonType type;

    /**
     * Combine les champs de recherche automatique avec une condition AND
     * @param fields Champs de recherches
     * @return La combinaison de champs
     */
    public static AutomaticSearchFieldCombinaison and(List<AutomaticSearchField<?>> fields) {
        AutomaticSearchFieldCombinaison combinator = new AutomaticSearchFieldCombinaison();
        combinator.setFields(fields);
        combinator.setType(SearchFieldCombinaisonType.AND);
        return combinator;
    }

    /**
     * Combine les champs de recherche automatique avec une condition OR
     * @param fields Champs de recherches
     * @return La combinaison de champs
     */
    public static AutomaticSearchFieldCombinaison or(List<AutomaticSearchField<?>> fields) {
        AutomaticSearchFieldCombinaison combinator = new AutomaticSearchFieldCombinaison();
        combinator.setFields(fields);
        combinator.setType(SearchFieldCombinaisonType.OR);
        return combinator;
    }
}
