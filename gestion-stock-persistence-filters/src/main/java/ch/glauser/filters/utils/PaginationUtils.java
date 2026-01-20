package ch.glauser.filters.utils;

import ch.glauser.filters.search.api.SearchQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Récupère la page par défaut
     * @param orders Ordre de tri
     * @return Une page
     */
    public static Pageable getDefaultPage(List<Sort.Order> orders) {
        return PageRequest.of(
                DEFAULT_PAGE_NUMBER,
                DEFAULT_PAGE_SIZE,
                Sort.by(orders)
        );
    }

    /**
     * Récupère la page
     * @param searchQuery Search query
     * @param orders Ordre de tri
     * @return Une page
     */
    public static Pageable getPage(SearchQuery searchQuery, List<Sort.Order> orders) {
        return PageRequest.of(
                Optional.ofNullable(searchQuery.getPage()).orElse(DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(searchQuery.getPageSize()).orElse(DEFAULT_PAGE_SIZE),
                Sort.by(orders)
        );
    }
}
