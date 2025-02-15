package ch.glauser.gestionstock.common.pagination;

import java.util.List;

/**
 * Classe utilisée pour traiter les résultats de requêtes de liste
 */
public class SearchResult<T> {
    private Long currentPage;
    private Long totalPages;
    private Long pageSize;
    private Long totalElements;
    private List<T> elements;
}
