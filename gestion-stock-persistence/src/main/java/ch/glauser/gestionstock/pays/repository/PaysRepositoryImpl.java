package ch.glauser.gestionstock.pays.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.pays.entity.PaysEntity;
import ch.glauser.gestionstock.pays.model.Pays;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * Implémentation du repository de gestion des pays
 */
@Repository
public class PaysRepositoryImpl implements PaysRepository {

    private final PaysJpaRepository paysJpaRepository;

    public PaysRepositoryImpl(PaysJpaRepository paysJpaRepository) {
        this.paysJpaRepository = paysJpaRepository;
    }

    @Override
    public Pays getPays(Long id) {
        return this.paysJpaRepository.findOptionalById(id).map(ModelEntity::toDomain).orElse(null);
    }

    @Override
    public SearchResult<Pays> searchPays(SearchRequest searchRequest) {
        Page<PaysEntity> page = this.paysJpaRepository.search(PageUtils.getFilters(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Pays createPays(Pays pays) {
        return this.paysJpaRepository.save(new PaysEntity(pays)).toDomain();
    }

    @Override
    public Pays modifyPays(Pays pays) {
        return this.paysJpaRepository.save(new PaysEntity(pays)).toDomain();
    }

    @Override
    public void deletePays(Long id) {
        this.paysJpaRepository.deleteById(id);
    }

    @Override
    public boolean existPaysByNom(String nom) {
        return this.paysJpaRepository.existsByNom(nom);
    }

    @Override
    public boolean existPaysByAbreviation(String abreviation) {
        return this.paysJpaRepository.existsByAbreviation(abreviation);
    }
}
