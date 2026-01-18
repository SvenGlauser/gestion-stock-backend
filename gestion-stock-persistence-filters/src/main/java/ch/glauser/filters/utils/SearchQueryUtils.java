package ch.glauser.filters.utils;

import ch.glauser.filters.api.field.Field;
import ch.glauser.filters.api.search.SearchQuery;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.filters.filter.api.FilterCombinaison;
import ch.glauser.filters.sort.api.Sort;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchQueryUtils {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static <T extends SearchQuery> Pageable paginate(T searchQuery,
                                                            List<SearchQueryMapper<T, ?>> mappers) {
        List<org.springframework.data.domain.Sort.Order> orders = mappers
                .stream()
                .filter(Objects::nonNull)
                .map(mapper -> getSort(searchQuery, mapper))
                .filter(Objects::nonNull)
                .map(Sort::getOrder)
                .toList();

        return PageRequest.of(
                Optional.ofNullable(searchQuery.getPage()).orElse(DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(searchQuery.getPageSize()).orElse(DEFAULT_PAGE_SIZE),
                org.springframework.data.domain.Sort.by(orders)
        );
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

    public static <T extends SearchQuery> List<FilterCombinaison> filterCombinaison(T searchQuery,
                                                                              List<SearchQueryMapper<T, ?>> mappers) {
        List<Filter<?>> combinaison = mappers
                .stream()
                .filter(Objects::nonNull)
                .map(mapper -> getFilter(searchQuery, mapper))
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedList::new));

        return List.of(FilterCombinaison.and(combinaison));
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

    /**
     * Créer des filtres pour les recherches
     *
     * @param combinaisons Filtres à appliquer
     * @return Une instance de Specification
     */
    public static <T> Specification<T> specificationOf(Collection<FilterCombinaison> combinaisons) {
        return (root, query, criteriaBuilder) -> toPredicates(combinaisons, root, criteriaBuilder);
    }

    private static <T> Predicate toPredicates(Collection<FilterCombinaison> combinaisons, Path<T> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (FilterCombinaison combinaison : combinaisons) {
            if (Objects.isNull(combinaison.getType())) {
                combinaison.setType(FilterCombinaison.Type.AND);
            }

            final Predicate[] combinaisonPredicates = getPredicates(combinaison.getFilters(), root, criteriaBuilder);

            switch (combinaison.getType()) {
                case AND -> predicates.add(criteriaBuilder.and(combinaisonPredicates));
                case OR -> predicates.add(criteriaBuilder.or(combinaisonPredicates));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private static <T> Predicate[] getPredicates(List<Filter<?>> filters, Path<T> root, CriteriaBuilder criteriaBuilder) {
        return filters
                .stream()
                .filter(Objects::nonNull)
                .map(filter -> filter.getPredicate(root, criteriaBuilder))
                .toArray(Predicate[]::new);
    }
}
