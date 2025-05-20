package ch.glauser.gestionstock.localite.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.localite.entity.LocaliteEntity;
import ch.glauser.gestionstock.localite.model.Localite;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public Optional<Localite> get(Long id) {
        return this.localiteJpaRepository.findById(id).map(ModelEntity::toDomain);
    }

    @Override
    public SearchResult<Localite> search(SearchRequest searchRequest) {
        Page<LocaliteEntity> page = this.localiteJpaRepository.search(PageUtils.getFiltersCombinators(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Localite create(Localite localite) {
        return this.localiteJpaRepository.save(new LocaliteEntity(localite)).toDomain();
    }

    @Override
    public Localite modify(Localite localite) {
        return this.localiteJpaRepository.save(new LocaliteEntity(localite)).toDomain();
    }

    @Override
    public void delete(Long id) {
        this.localiteJpaRepository.deleteById(id);
    }

    @Override
    public boolean existByIdPays(Long id) {
        return this.localiteJpaRepository.existsByIdPays(id);
    }

    @Override
    public boolean existByNpaAndNomAndIdPays(String npa, String nom, Long id) {
        return this.localiteJpaRepository.existsByNpaAndNomAndIdPays(npa, nom, id);
    }
}
