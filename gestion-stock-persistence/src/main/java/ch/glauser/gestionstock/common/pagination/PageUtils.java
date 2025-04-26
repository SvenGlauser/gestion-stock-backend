package ch.glauser.gestionstock.common.pagination;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.model.Model;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

/**
 * Utilitaire de gestion des pages
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageUtils {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static <T extends ModelEntity<R>, R extends Model> SearchResult<R> transform(Page<T> page) {
        SearchResult<R> searchResult = new SearchResult<>();
        searchResult.setCurrentPage(page.getNumber());
        searchResult.setTotalPages(page.getTotalPages());
        searchResult.setPageSize(page.getSize());
        searchResult.setTotalElements(page.getTotalElements());
        searchResult.setElements(page.getContent().stream().map(ModelEntity::toDomain).toList());

        return searchResult;
    }

    public static Pageable paginate(SearchRequest searchRequest) {
        List<Sort.Order> orders = new ArrayList<>();
        Collection<Filter> filters = getFilters(searchRequest);

        for (Filter filter : filters) {
            if (Objects.nonNull(filter.getOrder())) {
                if (filter.getOrder() == Filter.Order.ASC) {
                    orders.add(Sort.Order.asc(filter.getField()).ignoreCase());
                } else if (filter.getOrder() == Filter.Order.DESC) {
                    orders.add(Sort.Order.desc(filter.getField()).ignoreCase());
                }
            }
        }

        return PageRequest.of(
                Optional.ofNullable(searchRequest.getPage()).orElse(DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(searchRequest.getPageSize()).orElse(DEFAULT_PAGE_SIZE),
                Sort.by(orders)
        );
    }

    public static Pageable getDefaultPage(List<Sort.Order> orders) {
        return PageRequest.of(
                DEFAULT_PAGE_NUMBER,
                DEFAULT_PAGE_SIZE,
                Sort.by(orders)
        );
    }

    public static Collection<Filter> getFilters(SearchRequest searchRequest) {
        return CollectionUtils
                .emptyIfNull(searchRequest.getCombinators())
                .stream()
                .map(FilterCombinator::getFilters)
                .flatMap(Collection::stream)
                .toList();
    }

    public static Collection<FilterCombinator> getFiltersCombinators(SearchRequest searchRequest) {
        return CollectionUtils.emptyIfNull(searchRequest.getCombinators());
    }
}
