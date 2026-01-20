package ch.glauser.gestionstock.common.pagination;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.model.Model;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Utilitaire de gestion des pages
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageUtils {

    public static <T> SearchResult<T> of(List<T> elements,
                                         int currentPage,
                                         int pageSize,
                                         long totalElements) {
        SearchResult<T> searchResult = new SearchResult<>();
        searchResult.setCurrentPage(currentPage);
        searchResult.setTotalPages((int) (totalElements/pageSize));
        searchResult.setPageSize(pageSize);
        searchResult.setTotalElements(totalElements);
        searchResult.setElements(elements);

        return searchResult;
    }

    public static <T extends ModelEntity<R>, R extends Model> SearchResult<R> transform(Page<T> page) {
        SearchResult<R> searchResult = new SearchResult<>();
        searchResult.setCurrentPage(page.getNumber());
        searchResult.setTotalPages(page.getTotalPages());
        searchResult.setPageSize(page.getSize());
        searchResult.setTotalElements(page.getTotalElements());
        searchResult.setElements(page.getContent().stream().map(ModelEntity::toDomain).toList());

        return searchResult;
    }
}
