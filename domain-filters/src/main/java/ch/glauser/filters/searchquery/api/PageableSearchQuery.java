package ch.glauser.filters.searchquery.api;

/**
 * Search query pouvant être paginée
 */
public interface PageableSearchQuery {
    Integer getPage();
    Integer getPageSize();
}
