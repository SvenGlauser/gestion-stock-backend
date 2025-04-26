package ch.glauser.gestionstock.exception.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.exception.entity.ThrownExceptionEntity;
import ch.glauser.gestionstock.exception.model.ThrownException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * Implémentation du repository de gestion des catégories
 */
@Repository
@RequiredArgsConstructor
public class ThrownExceptionRepositoryImpl implements ThrownExceptionRepository {

    private final ThrownExceptionJpaRepository thrownExceptionJpaRepository;

    @Override
    public ThrownException getException(Long id) {
        return this.thrownExceptionJpaRepository.findOptionalById(id).map(ModelEntity::toDomain).orElse(null);
    }

    @Override
    public SearchResult<ThrownException> searchExceptions(SearchRequest searchRequest) {
        Page<ThrownExceptionEntity> page = this.thrownExceptionJpaRepository.search(PageUtils.getFiltersCombinators(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public ThrownException createException(ThrownException thrownException) {
        return this.thrownExceptionJpaRepository.save(new ThrownExceptionEntity(thrownException)).toDomain();
    }

    @Override
    public ThrownException modifyException(ThrownException thrownException) {
        return this.thrownExceptionJpaRepository.save(new ThrownExceptionEntity(thrownException)).toDomain();
    }
}
