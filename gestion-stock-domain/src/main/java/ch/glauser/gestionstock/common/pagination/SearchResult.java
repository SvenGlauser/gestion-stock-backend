package ch.glauser.gestionstock.common.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Classe utilisée pour traiter les résultats de requêtes de liste
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchResult<T> {
    private Integer currentPage;
    private Integer totalPages;
    private Integer pageSize;
    private Long totalElements;
    private List<T> elements;
}
