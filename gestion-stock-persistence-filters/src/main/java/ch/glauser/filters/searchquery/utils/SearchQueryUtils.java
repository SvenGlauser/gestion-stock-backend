package ch.glauser.filters.searchquery.utils;

import ch.glauser.filters.field.api.Field;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.filters.filter.api.FilterCombinaison;
import ch.glauser.filters.search.api.SearchQuery;
import ch.glauser.filters.sort.api.Sort;
import ch.glauser.filters.utils.PaginationUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchQueryUtils {

    /**
     * Pagine la search query
     * @param searchQuery Search query
     * @param mappers Mapper pour pouvoir récupérer les order by
     * @return Une page
     * @param <T> Type de la search query
     */
    public static <T extends SearchQuery> Pageable paginate(T searchQuery,
                                                            List<SearchQueryMapper<T, ?>> mappers) {
        List<org.springframework.data.domain.Sort.Order> orders = getSorts(searchQuery, mappers)
                .stream()
                .map(Sort::getOrder)
                .toList();

        return PaginationUtils.getPage(searchQuery, orders);
    }

    /**
     * Récupère une liste de tri
     * @param searchQuery Search query
     * @param mappers Mapper pour récupérer les champs de la search query
     * @return Une liste de tri
     * @param <T> Type de la search query
     */
    public static <T extends SearchQuery> List<Sort> getSorts(T searchQuery, List<SearchQueryMapper<T, ?>> mappers) {
        return mappers
                .stream()
                .filter(Objects::nonNull)
                .map(mapper -> getSort(searchQuery, mapper))
                .filter(Objects::nonNull)
                .toList();
    }

    private static <T extends SearchQuery> Sort getSort(T searchQuery, SearchQueryMapper<T, ?> mapper) {
        Field<?> field = mapper.getFieldGetter().apply(searchQuery);

        if (Objects.isNull(field) || Objects.isNull(field.getOrder())) {
            return null;
        }

        return Sort.of(
                mapper.getFieldGetter().apply(searchQuery),
                mapper
                        .getFields()
                        .stream()
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.joining(".")));
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
        List<Filter<?>> combinaison = getFilters(searchQuery, mappers);

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
        return mappers
                .stream()
                .filter(Objects::nonNull)
                .map(mapper -> getFilter(searchQuery, mapper))
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static <T extends SearchQuery, R> Filter<R> getFilter(T searchQuery, SearchQueryMapper<T, R> mapper) {
        Field<R> field = mapper.getFieldGetter().apply(searchQuery);

        if (Objects.isNull(field) || Objects.isNull(field.getValue())) {
            return null;
        }

        final Filter<R> filter = mapper.getFilterSupplier().get();
        filter.setField(mapper
                .getFields()
                .stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(".")));
        filter.setValue(field.getValue());
        return filter;
    }
}
