package ch.glauser.filters.search.api;

/**
 * Search query pouvant être paginée
 */
public interface PageableSearchQuery {
    Integer getPage();
    Integer getPageSize();
}
