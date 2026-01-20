package ch.glauser.filters.searchquery.utils;

import ch.glauser.filters.automatic.AutomaticSearchField;
import ch.glauser.filters.automatic.AutomaticSearchFieldCombinaison;
import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.filters.filter.api.FilterCombinaison;
import ch.glauser.filters.filter.charsequence.FilterLike;
import ch.glauser.filters.filter.object.FilterEquals;
import ch.glauser.filters.sort.api.SortField;
import ch.glauser.filters.utils.PaginationUtils;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.exception.ValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Utilitaire pour les search query de champs automatiques
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AutomatedSearchQueryUtils {

    /**
     * Pagine la search query
     * @param searchQuery Search query
     * @return Une page
     * @throws ValidationException Si la search query est nulle
     */
    public static Pageable paginate(AutomaticSearchQuery searchQuery) throws ValidationException {
        Validation
                .of(AutomatedSearchQueryUtils.class)
                .validateNotNull(searchQuery, "searchQuery")
                .execute();

        List<Sort.Order> orders = AutomatedSearchQueryUtils
                .getSorts(searchQuery)
                .stream()
                .map(SortField::getJpaPaginationOrder)
                .toList();

        return PaginationUtils.getPage(searchQuery, orders);
    }

    /**
     * Récupère une liste de tri
     * @param searchQuery Search query
     * @return Une liste de tri
     * @throws ValidationException Si la search query est nulle
     */
    public static List<SortField> getSorts(AutomaticSearchQuery searchQuery) throws ValidationException {
        Validation
            .of(AutomatedSearchQueryUtils.class)
            .validateNotNull(searchQuery, "searchQuery")
            .execute();

        return searchQuery
                .getCombinators()
                .stream()
                .filter(Objects::nonNull)
                // Récupération des champs
                .map(AutomaticSearchFieldCombinaison::getFields)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                // Transformation en Sort
                .map(SortField::of)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * Récupère une liste de combinaison de filtre depuis une SearchQuery
     * @param searchQuery Search query
     * @return Une liste de combinaison de filtres
     * @throws ValidationException Si la search query est nulle
     */
    public static List<FilterCombinaison> getFiltersCombinators(AutomaticSearchQuery searchQuery) throws ValidationException {
        Validation
                .of(AutomatedSearchQueryUtils.class)
                .validateNotNull(searchQuery, "searchQuery")
                .execute();

        return AutomatedSearchQueryUtils.getFiltersCombinators(searchQuery.getCombinators());
    }

    /**
     * Récupère une liste de combinaison de filtre
     * @param fields Champs
     * @return Une liste de combinaison de filtres
     */
    public static List<FilterCombinaison> getFiltersCombinators(Collection<AutomaticSearchFieldCombinaison> fields) {
        return CollectionUtils
                .emptyIfNull(fields)
                .stream()
                .filter(Objects::nonNull)
                .map(automaticFieldCombinator -> {
                    FilterCombinaison filterCombinaison = new FilterCombinaison();
                    filterCombinaison.setType(automaticFieldCombinator.getType());

                    List<Filter<?>> filters = CollectionUtils
                            .emptyIfNull(automaticFieldCombinator.getFields())
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

    private static <T> Filter<?> getFilter(AutomaticSearchField<T> field) {
        if (Objects.isNull(field)
            || Objects.isNull(field.getType())
            || Objects.isNull(field.getField())) {
            return null;
        }

        return switch (field.getType()) {
            case EQUAL -> FilterEquals.of(field, field.getField());
            case STRING_LIKE -> FilterLike.of((SearchField<? extends CharSequence>) field, field.getField());
        };
    }
}
