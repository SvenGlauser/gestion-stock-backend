package ch.glauser.gestionstock.localite.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.localite.entity.LocaliteEntity;
import ch.glauser.gestionstock.localite.model.Localite;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * Implémentation du repository de gestion des localités
 */
@Repository
public class LocaliteRepositoryImpl implements LocaliteRepository {

    private final LocaliteJpaRepository localiteJpaRepository;

    public LocaliteRepositoryImpl(LocaliteJpaRepository localiteJpaRepository) {
        this.localiteJpaRepository = localiteJpaRepository;
    }

    @Override
    public Localite getLocalite(Long id) {
        return this.localiteJpaRepository.findOptionalById(id).map(ModelEntity::toDomain).orElse(null);
    }

    @Override
    public SearchResult<Localite> searchLocalite(SearchRequest searchRequest) {
        Page<LocaliteEntity> page = this.localiteJpaRepository.search(PageUtils.getFilters(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Localite createLocalite(Localite localite) {
        return this.localiteJpaRepository.save(new LocaliteEntity(localite)).toDomain();
    }

    @Override
    public Localite modifyLocalite(Localite localite) {
        return this.localiteJpaRepository.save(new LocaliteEntity(localite)).toDomain();
    }

    @Override
    public void deleteLocalite(Long id) {
        this.localiteJpaRepository.deleteById(id);
    }
}
