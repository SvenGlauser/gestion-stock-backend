package ch.glauser.filters.filter.api;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Combinaison de filtres de recherche
 */
@Getter
@Setter
public class FilterCombinaison {
    private Collection<Filter<?>> filters;
    private SearchFieldCombinaisonType type;

    /**
     * Crée une combinaison de filtres vides
     */
    public FilterCombinaison() {
        this.filters = new ArrayList<>();
        this.type = SearchFieldCombinaisonType.AND;
    }

    /**
     * Récupère la liste des filtres qui ne sont pas considérés comme vide
     * @return Une liste de filtre
     */
    public Collection<Filter<?>> getNotEmptyFilters() {
        return CollectionUtils
                .emptyIfNull(this.filters)
                .stream()
                .filter(Filter::isNotEmpty)
                .toList();
    }

    /**
     * Combine des filtres avec une clause AND
     * @param filters Filtres à combiner
     * @return La combinaison de filtres
     */
    public static FilterCombinaison and(Collection<Filter<?>> filters) {
        FilterCombinaison combinator = new FilterCombinaison();
        combinator.setFilters(filters);
        combinator.setType(SearchFieldCombinaisonType.AND);
        return combinator;
    }

    /**
     * Combine des filtres avec une clause OR
     * @param filters Filtres à combiner
     * @return La combinaison de filtres
     */
    public static FilterCombinaison or(Collection<Filter<?>> filters) {
        FilterCombinaison combinator = new FilterCombinaison();
        combinator.setFilters(filters);
        combinator.setType(SearchFieldCombinaisonType.OR);
        return combinator;
    }
}
