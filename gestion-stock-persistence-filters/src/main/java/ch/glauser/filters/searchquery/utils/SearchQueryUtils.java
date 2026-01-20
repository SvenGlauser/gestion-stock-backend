package ch.glauser.filters.searchquery.utils;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.filters.filter.api.FilterCombinaison;
import ch.glauser.filters.search.api.SearchQuery;
import ch.glauser.filters.sort.api.SortField;
import ch.glauser.filters.utils.PaginationUtils;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.exception.ValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utilitaire permettant de traiter les SearchQuery
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchQueryUtils {

    /**
     * Pagine la search query
     * @param searchQuery Search query
     * @param mappers Mapper pour pouvoir récupérer les order by
     * @return Une page
     * @param <T> Type de la search query
     * @throws ValidationException Si les paramètres sont nuls
     */
    public static <T extends SearchQuery> Pageable paginate(T searchQuery,
                                                            List<SearchQueryMapper<T, ?>> mappers) throws ValidationException {
        Validation
                .of(SearchQueryUtils.class)
                .validateNotNull(searchQuery, "searchQuery")
                .validateNotNull(mappers, "mappers")
                .execute();

        List<Sort.Order> orders = SearchQueryUtils
                .getSorts(searchQuery, mappers)
                .stream()
                .map(SortField::getJpaPaginationOrder)
                .collect(Collectors.toCollection(LinkedList::new));

        return PaginationUtils.getPage(searchQuery, orders);
    }

    /**
     * Récupère une liste de tri
     * @param searchQuery Search query
     * @param mappers Mapper pour récupérer les champs de la search query
     * @return Une liste de tri
     * @param <T> Type de la search query
     * @throws ValidationException Si les paramètres sont nuls
     */
    public static <T extends SearchQuery> List<SortField> getSorts(T searchQuery,
                                                                   List<SearchQueryMapper<T, ?>> mappers) throws ValidationException {
        Validation
                .of(SearchQueryUtils.class)
                .validateNotNull(searchQuery, "searchQuery")
                .validateNotNull(mappers, "mappers")
                .execute();

        return mappers
                .stream()
                .filter(Objects::nonNull)
                .map(mapper -> SearchQueryUtils.getSort(searchQuery, mapper))
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static <T extends SearchQuery> SortField getSort(T searchQuery, SearchQueryMapper<T, ?> mapper) {
        SearchField<?> searchField = mapper.fieldGetter().apply(searchQuery);

        if (Objects.isNull(searchField) || Objects.isNull(searchField.getOrder())) {
            return null;
        }

        return SortField.of(
                searchField,
                Strings.join(mapper.fieldsName(), '.'));
    }

    /**
     * Récupère une liste de combinaison de filtre depuis une SearchQuery et une liste de mapper
     * @param searchQuery Search query
     * @param mappers Mapper pour transformer la SearchQuery en filtre
     * @return Une liste de combinaison de filtres
     * @param <T> Type de la SearchQuery
     */
    public static <T extends SearchQuery> List<FilterCombinaison> filterCombinaison(T searchQuery,
                                                                                    List<SearchQueryMapper<T, ?>> mappers) {
        Validation
                .of(SearchQueryUtils.class)
                .validateNotNull(searchQuery, "searchQuery")
                .validateNotNull(mappers, "mappers")
                .execute();

        List<Filter<?>> combinaison = SearchQueryUtils.getFilters(searchQuery, mappers);

        return List.of(FilterCombinaison.and(combinaison));
    }

    /**
     * Récupère une liste de filtre depuis une SearchQuery et une liste de mapper
     * @param searchQuery Search query
     * @param mappers Mapper pour transformer la SearchQuery en filtre
     * @return Une liste de filtre
     * @param <T> Type de la SearchQuery
     */
    public static <T extends SearchQuery> List<Filter<?>> getFilters(T searchQuery,
                                                                     List<SearchQueryMapper<T, ?>> mappers) {
        Validation
                .of(SearchQueryUtils.class)
                .validateNotNull(searchQuery, "searchQuery")
                .validateNotNull(mappers, "mappers")
                .execute();

        return mappers
                .stream()
                .filter(Objects::nonNull)
                .map(mapper -> SearchQueryUtils.getFilter(searchQuery, mapper))
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static <T extends SearchQuery, R> Filter<R> getFilter(T searchQuery, SearchQueryMapper<T, R> mapper) {
        SearchField<R> searchField = mapper.fieldGetter().apply(searchQuery);

        if (Objects.isNull(searchField) || Objects.isNull(searchField.getValue())) {
            return null;
        }

        final Filter<R> filter = mapper.filterSupplier().get();
        filter.setField(Strings.join(mapper.fieldsName(), '.'));
        filter.setValue(searchField.getValue());
        return filter;
    }
}
