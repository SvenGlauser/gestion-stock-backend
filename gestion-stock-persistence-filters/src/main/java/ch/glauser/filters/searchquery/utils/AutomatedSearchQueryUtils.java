package ch.glauser.filters.searchquery.utils;

import ch.glauser.filters.automatic.AutomaticField;
import ch.glauser.filters.automatic.AutomaticFieldCombinator;
import ch.glauser.filters.automatic.SearchRequest;
import ch.glauser.filters.field.api.Field;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.filters.filter.api.FilterCombinaison;
import ch.glauser.filters.filter.charsequence.FilterLike;
import ch.glauser.filters.filter.object.FilterEquals;
import ch.glauser.filters.sort.api.Sort;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AutomatedSearchQueryUtils {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Pagine la search query
     * @param searchQuery Search query
     * @return Une page
     */
    public static Pageable paginate(SearchRequest searchQuery) {
        List<org.springframework.data.domain.Sort.Order> orders = getSorts(searchQuery)
                .stream()
                .map(Sort::getOrder)
                .toList();

        return PageRequest.of(
                Optional.ofNullable(searchQuery.getPage()).orElse(DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(searchQuery.getPageSize()).orElse(DEFAULT_PAGE_SIZE),
                org.springframework.data.domain.Sort.by(orders)
        );
    }

    /**
     * Récupère une liste de tri
     * @param searchQuery Search query
     * @return Une liste de tri
     */
    public static List<Sort> getSorts(SearchRequest searchQuery) {
        return Optional
                // Récupération des listes de combinators
                .ofNullable(searchQuery)
                .map(SearchRequest::getCombinators)
                .orElseGet(ArrayList::new)
                .stream()
                // Récupération des filtres
                .filter(Objects::nonNull)
                .map(AutomaticFieldCombinator::getFilters)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                // Transformation en Sort
                .map(Sort::of)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * Récupère une liste de combinaison de filtre depuis une SearchQuery
     * @param searchQuery Search query
     * @return Une liste de combinaison de filtres
     */
    public static List<FilterCombinaison> getFiltersCombinators(SearchRequest searchQuery) {
        List<AutomaticFieldCombinator> fields = Optional
                // Récupération des listes de combinators
                .ofNullable(searchQuery)
                .map(SearchRequest::getCombinators)
                .orElseGet(ArrayList::new);

        return getFiltersCombinators(fields);
    }

    /**
     * Récupère une liste de combinaison de filtre
     * @param fields Champs
     * @return Une liste de combinaison de filtres
     */
    public static List<FilterCombinaison> getFiltersCombinators(Collection<AutomaticFieldCombinator> fields) {
        return fields
                .stream()
                .map(automaticFieldCombinator -> {
                    FilterCombinaison filterCombinaison = new FilterCombinaison();
                    filterCombinaison.setType(automaticFieldCombinator.getType());

                    List<Filter<?>> filters = CollectionUtils
                            .emptyIfNull(automaticFieldCombinator.getFilters())
                            .stream()
                            .filter(Objects::nonNull)
                            .<Filter<?>>map(AutomatedSearchQueryUtils::getFilter)
                            .filter(Objects::nonNull)
                            .toList();

                    filterCombinaison.setFilters(filters);

                    return filterCombinaison;
                })
                .toList();
    }

    private static <T> Filter<?> getFilter(AutomaticField<T> field) {
        if (Objects.isNull(field)
            || Objects.isNull(field.getType())
            || Objects.isNull(field.getValue())
            || Objects.isNull(field.getField())) {
            return null;
        }

        return switch (field.getType()) {
            case EQUAL -> FilterEquals.of(field, field.getField());
            case STRING_LIKE -> FilterLike.of((Field<? extends CharSequence>) field, field.getField());
        };
    }
}
