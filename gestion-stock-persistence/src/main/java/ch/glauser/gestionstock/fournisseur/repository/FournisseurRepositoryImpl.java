package ch.glauser.gestionstock.fournisseur.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.entity.FournisseurEntity;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

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
    public Fournisseur getFournisseur(Long id) {
        return this.fournisseurJpaRepository.findOptionalById(id).map(ModelEntity::toDomain).orElse(null);
    }

    @Override
    public SearchResult<Fournisseur> searchFournisseur(SearchRequest searchRequest) {
        Page<FournisseurEntity> page = this.fournisseurJpaRepository.search(PageUtils.getFilters(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Fournisseur createFournisseur(Fournisseur fournisseur) {
        return this.fournisseurJpaRepository.save(new FournisseurEntity(fournisseur)).toDomain();
    }

    @Override
    public Fournisseur modifyFournisseur(Fournisseur fournisseur) {
        return this.fournisseurJpaRepository.save(new FournisseurEntity(fournisseur)).toDomain();
    }

    @Override
    public void deleteFournisseur(Long id) {
        this.fournisseurJpaRepository.deleteById(id);
    }

    @Override
    public boolean existFournisseurWithIdLocalite(Long id) {
        return this.fournisseurJpaRepository.existsByIdLocalite(id);
    }
}
