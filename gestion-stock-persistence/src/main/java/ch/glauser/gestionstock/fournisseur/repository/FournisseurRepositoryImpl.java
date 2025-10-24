package ch.glauser.gestionstock.fournisseur.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.entity.FournisseurEntity;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implémentation du repository de gestion des fournisseurs
 */
@Repository
public class FournisseurRepositoryImpl implements FournisseurRepository {

    private final FournisseurJpaRepository fournisseurJpaRepository;

    public FournisseurRepositoryImpl(FournisseurJpaRepository fournisseurJpaRepository) {
        this.fournisseurJpaRepository = fournisseurJpaRepository;
    }

    @Override
    public Optional<Fournisseur> get(Long id) {
        return this.fournisseurJpaRepository.findById(id).map(ModelEntity::toDomain);
    }

    @Override
    public Set<Fournisseur> findAllByIdentiteDesignation(String designation) {
        return this.fournisseurJpaRepository
                .findAllByIdentiteDesignation(designation)
                .stream()
                .map(ModelEntity::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public SearchResult<Fournisseur> search(SearchRequest searchRequest) {
        Page<FournisseurEntity> page = this.fournisseurJpaRepository.search(PageUtils.getFiltersCombinators(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Fournisseur create(Fournisseur fournisseur) {
        return this.fournisseurJpaRepository.save(new FournisseurEntity(fournisseur)).toDomain();
    }

    @Override
    public Fournisseur modify(Fournisseur fournisseur) {
        return this.fournisseurJpaRepository.save(new FournisseurEntity(fournisseur)).toDomain();
    }

    @Override
    public void delete(Long id) {
        this.fournisseurJpaRepository.deleteById(id);
    }

    @Override
    public boolean existByIdIdentite(Long id) {
        return this.fournisseurJpaRepository.existsByIdIdentite(id);
    }
}
