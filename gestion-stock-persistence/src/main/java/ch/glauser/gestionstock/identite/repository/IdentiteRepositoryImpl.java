package ch.glauser.gestionstock.identite.repository;

import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.identite.entity.IdentiteEntity;
import ch.glauser.gestionstock.identite.model.Identite;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * Implémentation du repository de gestion des identités
 */
@Repository
public class IdentiteRepositoryImpl implements IdentiteRepository {

    private final IdentiteJpaRepository identiteJpaRepository;

    public IdentiteRepositoryImpl(IdentiteJpaRepository identiteJpaRepository) {
        this.identiteJpaRepository = identiteJpaRepository;
    }

    @Override
    public SearchResult<Identite> search(SearchRequest searchRequest) {
        Page<IdentiteEntity> page = this.identiteJpaRepository.search(PageUtils.getFiltersCombinators(searchRequest), PageUtils.paginate(searchRequest));

        SearchResult<Identite> searchResult = new SearchResult<>();
        searchResult.setCurrentPage(page.getNumber());
        searchResult.setTotalPages(page.getTotalPages());
        searchResult.setPageSize(page.getSize());
        searchResult.setTotalElements(page.getTotalElements());
        searchResult.setElements(page
                .stream()
                .map(IdentiteEntity::toDomain)
                .map(Identite.class::cast)
                .toList());

        return searchResult;
    }

    @Override
    public boolean existByIdLocalite(Long id) {
        return this.identiteJpaRepository.existsByIdLocalite(id);
    }
}
