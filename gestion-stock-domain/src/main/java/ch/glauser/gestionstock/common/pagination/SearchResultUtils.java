package ch.glauser.gestionstock.common.pagination;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Function;

/**
 * Utilitaire pour les {@link SearchResult}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SearchResultUtils {

    /**
     * Transforme le résultat d'un {@link SearchResult}
     *
     * @param searchResult {@link SearchResult} à transformer
     * @param transformer Function pour transformer d'un type à un autre
     *
     * @return Un nouveau {@link SearchResult} avec des DTO
     *
     * @param <T> Type initial
     * @param <R> Type du résultat
     */
    public static <T, R> SearchResult<R> transformDto(SearchResult<T> searchResult, Function<T, R> transformer) {
        SearchResult<R> searchResultDto = new SearchResult<>();
        searchResultDto.setCurrentPage(searchResult.getCurrentPage());
        searchResultDto.setTotalPages(searchResult.getTotalPages());
        searchResultDto.setPageSize(searchResult.getPageSize());
        searchResultDto.setTotalElements(searchResult.getTotalElements());
        searchResultDto.setElements(searchResult.getElements().stream().map(transformer).toList());

        return searchResultDto;
    }
}
